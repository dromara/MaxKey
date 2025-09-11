/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Component, OnInit, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { SettingsService } from '@delon/theme';
import { Subject, takeUntil, finalize } from 'rxjs';

// 定义接口类型
interface PasskeyInfo {
  id: string;
  credentialId: string;
  displayName: string;
  deviceType: string;
  signatureCount: number;
  createdDate: string;
  lastUsedDate?: string;
  status: number; // 修复：状态应为数字类型，1表示活跃，0表示禁用
}

interface ApiResponse<T = any> {
  code: number;
  message?: string;
  data?: T;
}

interface UserInfo {
  userId?: string;
  id?: string;
  username?: string;
  displayName?: string;
}

@Component({
  selector: 'app-passkey',
  templateUrl: './passkey.component.html',
  styleUrls: ['./passkey.component.less']
})
export class PasskeyComponent implements OnInit, OnDestroy {
  loading = false;
  passkeyList: PasskeyInfo[] = [];
  private destroy$ = new Subject<void>();

  constructor(
    private msg: NzMessageService,
    private modal: NzModalService,
    private cdr: ChangeDetectorRef,
    private http: HttpClient,
    private settingsService: SettingsService
  ) {}

  ngOnInit(): void {
    this.loadPasskeys();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadPasskeys(): void {
    const userId = this.getCurrentUserId();
    if (!userId) {
      this.msg.error('无法获取当前用户ID，请重新登录');
      return;
    }

    this.loading = true;
    this.http.get<ApiResponse<PasskeyInfo[]>>(`/passkey/registration/list/${userId}`)
      .pipe(
        takeUntil(this.destroy$),
        finalize(() => {
          this.loading = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (response) => {
          if (response.code === 0) {
            this.passkeyList = response.data || [];
          } else {
            this.passkeyList = [];
            this.msg.warning(response.message || '获取Passkey列表失败');
          }
        },
        error: (error: HttpErrorResponse) => {
          console.error('加载Passkey列表失败:', error);
          this.passkeyList = [];
          this.handleHttpError(error, '加载Passkey列表失败');
        }
      });
  }

  async registerPasskey(): Promise<void> {
    console.log('=== PASSKEY REGISTRATION DEBUG START ===');
    console.log('Passkey registration clicked at:', new Date().toISOString());
    
    const userId = this.getCurrentUserId();
    console.log('Current user ID:', userId);
    
    if (!userId) {
      console.error('No user ID available');
      this.msg.error('无法获取当前用户ID，请重新登录');
      return;
    }

    // 检查浏览器是否支持 WebAuthn
    if (!this.isWebAuthnSupported()) {
      console.error('WebAuthn not supported');
      this.msg.error('您的浏览器不支持 WebAuthn/Passkey 功能');
      return;
    }
    console.log('WebAuthn support confirmed');

    if (this.loading) {
      console.log('Registration already in progress, ignoring click');
      return; // 防止重复点击
    }

    try {
      this.loading = true;
      this.cdr.detectChanges();

      const currentUser = this.settingsService.user as UserInfo;
      console.log('Current user info:', {
        userId: currentUser?.userId,
        username: currentUser?.username,
        displayName: currentUser?.displayName
      });
      
      // 调用后端 API 获取注册选项
      console.log('Step 1: Requesting registration options from backend...');
      const registrationRequest = {
        userId: userId,
        username: currentUser?.username || 'unknown_user',
        displayName: currentUser?.displayName || '未知用户'
      };
      console.log('Registration request payload:', registrationRequest);
      
      const beginResponse = await this.http.post<ApiResponse>('/passkey/registration/begin', registrationRequest).toPromise();
      console.log('Backend registration options response:', beginResponse);
      
      if (!beginResponse || beginResponse.code !== 0) {
        console.error('Failed to get registration options:', beginResponse);
        throw new Error(beginResponse?.message || '获取注册选项失败');
      }
      
      const regOptions = beginResponse.data;
      console.log('Registration options received:', regOptions);
      
      if (!regOptions) {
        console.error('Empty registration options');
        throw new Error('注册选项为空');
      }

      // 转换Base64字符串为ArrayBuffer
      console.log('Step 2: Converting registration options...');
      console.log('Original registration options:', {
        challengeLength: regOptions.challenge?.length,
        userIdLength: regOptions.user?.id?.length,
        excludeCredentialsCount: regOptions.excludeCredentials?.length || 0,
        timeout: regOptions.timeout,
        rpId: regOptions.rp?.id,
        rpName: regOptions.rp?.name
      });
      
      const convertedOptions = this.convertRegistrationOptions(regOptions);
      console.log('Converted registration options:', {
        challengeLength: convertedOptions.challenge.byteLength,
        userIdLength: convertedOptions.user.id.byteLength,
        userName: convertedOptions.user.name,
        userDisplayName: convertedOptions.user.displayName,
        excludeCredentialsCount: convertedOptions.excludeCredentials?.length || 0,
        timeout: convertedOptions.timeout,
        rpId: convertedOptions.rp.id,
        rpName: convertedOptions.rp.name,
        pubKeyCredParamsCount: convertedOptions.pubKeyCredParams?.length || 0
      });

      // 调用 WebAuthn API 进行注册
      console.log('Step 3: Calling WebAuthn API navigator.credentials.create()...');
      const credential = await navigator.credentials.create({
        publicKey: convertedOptions
      }) as PublicKeyCredential;

      if (!credential) {
        console.error('No credential returned from WebAuthn API');
        throw new Error('凭证创建失败');
      }
      
      console.log('=== REGISTRATION CREDENTIAL DEBUG INFO ===');
      console.log('Credential ID:', credential.id);
      console.log('Credential ID length:', credential.id.length);
      console.log('Credential type:', credential.type);
      console.log('Credential rawId length:', credential.rawId.byteLength);
      console.log('Credential rawId as base64:', this.arrayBufferToBase64(credential.rawId));
      
      // 验证 credential.id 和 rawId 的一致性
      const rawIdBase64 = this.arrayBufferToBase64(credential.rawId);
      console.log('ID consistency check:');
      console.log('  credential.id:', credential.id);
      console.log('  rawId as base64:', rawIdBase64);
      console.log('  IDs match:', credential.id === rawIdBase64);
      
      const credentialResponse = credential.response as AuthenticatorAttestationResponse;
      console.log('Authenticator response type:', credentialResponse.constructor.name);
      console.log('Attestation object length:', credentialResponse.attestationObject.byteLength);
      console.log('Client data JSON length:', credentialResponse.clientDataJSON.byteLength);
      console.log('=== END REGISTRATION CREDENTIAL DEBUG INFO ===');

      // 将注册结果发送到后端保存
      console.log('Step 4: Sending registration result to backend...');
      const finishRequest = {
        userId: userId,
        challengeId: regOptions.challengeId,
        credentialId: credential.id,
        attestationObject: this.arrayBufferToBase64(credentialResponse.attestationObject),
        clientDataJSON: this.arrayBufferToBase64(credentialResponse.clientDataJSON)
      };
      console.log('Registration finish request payload:', {
        userId: finishRequest.userId,
        challengeId: finishRequest.challengeId,
        credentialId: finishRequest.credentialId,
        credentialIdLength: finishRequest.credentialId.length,
        attestationObjectLength: finishRequest.attestationObject.length,
        clientDataJSONLength: finishRequest.clientDataJSON.length
      });
      
      const finishResponse = await this.http.post<ApiResponse<PasskeyInfo>>('/passkey/registration/finish', finishRequest).toPromise();
      console.log('Backend registration finish response:', finishResponse);
      
      if (!finishResponse || finishResponse.code !== 0) {
        console.error('Backend registration verification failed:', finishResponse);
        throw new Error(finishResponse?.message || 'Passkey注册失败');
      }

      const passkeyInfo = finishResponse.data;
      console.log('Registration successful, passkey info:', passkeyInfo);
      
      if (passkeyInfo) {
        this.msg.success(`Passkey 注册成功！`);
        
        // 添加新注册的Passkey到列表中
        console.log('Adding new passkey to list, current list length:', this.passkeyList.length);
        this.passkeyList.unshift(passkeyInfo);
        console.log('New list length:', this.passkeyList.length);
        this.cdr.detectChanges();
        console.log('=== PASSKEY REGISTRATION SUCCESS ===');
      }
    } catch (error: any) {
      console.error('=== PASSKEY REGISTRATION ERROR ===');
      console.error('Error type:', error.constructor.name);
      console.error('Error message:', error.message);
      console.error('Error stack:', error.stack);
      console.error('Full error object:', error);
      
      // 如果是 WebAuthn 相关错误，提供更详细的信息
      if (error.name) {
        console.error('WebAuthn error name:', error.name);
        switch (error.name) {
          case 'NotAllowedError':
            console.error('User cancelled the operation or timeout occurred');
            break;
          case 'SecurityError':
            console.error('Security error - invalid domain or HTTPS required');
            break;
          case 'NotSupportedError':
            console.error('Operation not supported by authenticator');
            break;
          case 'InvalidStateError':
            console.error('Authenticator is in invalid state');
            break;
          case 'ConstraintError':
            console.error('Constraint error in authenticator');
            break;
          case 'NotReadableError':
            console.error('Authenticator data not readable');
            break;
          default:
            console.error('Unknown WebAuthn error');
        }
      }
      
      console.error('Passkey 注册失败:', error);
      this.handlePasskeyError(error);
      console.log('=== PASSKEY REGISTRATION DEBUG END ===');
    } finally {
      this.loading = false;
      this.cdr.detectChanges();
      console.log('Registration loading state reset');
    }
  }

  deletePasskey(credentialId: string): void {
    if (!credentialId) {
      this.msg.error('凭证ID无效');
      return;
    }

    const userId = this.getCurrentUserId();
    if (!userId) {
      this.msg.error('无法获取当前用户ID，请重新登录');
      return;
    }

    this.http.delete<ApiResponse>(`/passkey/registration/delete/${userId}/${credentialId}`)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          if (response.code === 0) {
            this.msg.success('Passkey 删除成功');
            // 从本地列表中移除，避免重新加载
            this.passkeyList = this.passkeyList.filter(item => item.credentialId !== credentialId);
            this.cdr.detectChanges();
          } else {
            this.msg.error(response.message || 'Passkey 删除失败');
          }
        },
        error: (error: HttpErrorResponse) => {
          console.error('删除Passkey失败:', error);
          this.handleHttpError(error, 'Passkey 删除失败');
        }
      });
  }

  confirmDeletePasskey(credentialId: string): void {
    this.modal.confirm({
      nzTitle: '确认删除',
      nzContent: '确定要删除这个 Passkey 吗？此操作不可撤销。',
      nzOkText: '删除',
      nzOkType: 'primary',
      nzOkDanger: true,
      nzCancelText: '取消',
      nzOnOk: () => {
        this.deletePasskey(credentialId);
      }
    });
  }

  /**
   * 获取当前用户ID
   */
  private getCurrentUserId(): string | null {
    const currentUser = this.settingsService.user as UserInfo;
    return currentUser?.userId || currentUser?.id || null;
  }

  /**
   * 检查浏览器是否支持WebAuthn
   */
  private isWebAuthnSupported(): boolean {
    return !!(window.PublicKeyCredential && navigator.credentials && navigator.credentials.create);
  }

  /**
   * 转换注册选项中的Base64字符串为ArrayBuffer
   */
  private convertRegistrationOptions(regOptions: any): PublicKeyCredentialCreationOptions {
    return {
      ...regOptions,
      challenge: this.base64ToArrayBuffer(regOptions.challenge),
      user: {
        ...regOptions.user,
        id: this.base64ToArrayBuffer(regOptions.user.id)
      },
      excludeCredentials: regOptions.excludeCredentials?.map((cred: any) => ({
        ...cred,
        id: this.base64ToArrayBuffer(cred.id)
      })) || []
    };
  }

  /**
   * 处理Passkey相关错误
   */
  private handlePasskeyError(error: any): void {
    if (error.name === 'NotAllowedError') {
      this.msg.error('Passkey 注册被取消或失败');
    } else if (error.name === 'NotSupportedError') {
      this.msg.error('您的设备不支持 Passkey 功能');
    } else if (error.name === 'SecurityError') {
      this.msg.error('安全错误，请检查HTTPS连接');
    } else if (error.name === 'InvalidStateError') {
      this.msg.error('设备状态无效，请重试');
    } else {
      this.msg.error(error.message || 'Passkey 注册失败，请重试');
    }
  }

  /**
   * 处理HTTP错误
   */
  private handleHttpError(error: HttpErrorResponse, defaultMessage: string): void {
    if (error.status === 401) {
      this.msg.error('认证失败，请重新登录');
    } else if (error.status === 403) {
      this.msg.error('权限不足');
    } else if (error.status === 404) {
      this.msg.error('接口不存在');
    } else if (error.status >= 500) {
      this.msg.error('服务器错误，请稍后重试');
    } else {
      this.msg.error(defaultMessage);
    }
  }



  /**
   * 将Base64URL字符串转换为ArrayBuffer
   */
  private base64ToArrayBuffer(base64: string): ArrayBuffer {
    try {
      // 将Base64URL转换为标准Base64
      let normalizedBase64 = base64
        .replace(/-/g, '+')  // 替换 - 为 +
        .replace(/_/g, '/'); // 替换 _ 为 /
      
      // 添加必要的填充
      const padded = normalizedBase64 + '='.repeat((4 - normalizedBase64.length % 4) % 4);
      const binaryString = atob(padded);
      const bytes = new Uint8Array(binaryString.length);
      for (let i = 0; i < binaryString.length; i++) {
        bytes[i] = binaryString.charCodeAt(i);
      }
      return bytes.buffer;
    } catch (error) {
      console.error('Base64解码失败:', error);
      throw new Error('Base64解码失败');
    }
  }

  /**
   * 将ArrayBuffer转换为Base64URL字符串（WebAuthn标准格式）
   */
  private arrayBufferToBase64(buffer: ArrayBuffer): string {
    try {
      const bytes = new Uint8Array(buffer);
      let binary = '';
      for (let i = 0; i < bytes.byteLength; i++) {
        binary += String.fromCharCode(bytes[i]);
      }
      // 转换为标准Base64，然后转换为Base64URL格式
      return btoa(binary)
        .replace(/\+/g, '-')  // 替换 + 为 -
        .replace(/\//g, '_')  // 替换 / 为 _
        .replace(/=/g, '');   // 移除填充字符 =
    } catch (error) {
      console.error('ArrayBuffer编码失败:', error);
      throw new Error('ArrayBuffer编码失败');
    }
  }
}