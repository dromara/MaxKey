/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
import { validatenull } from '@/util/validate'
import request from '@/router/axios'
import store from '@/store'
import qs from 'qs'
import { getStore, setStore } from '@/util/store.js'
import website from '@/config/website'


const scope = 'server'

export const loginByUsername = (username, password, code, randomStr) => {
  const grant_type = 'password'
  const dataObj = qs.stringify({ 'username': username, 'password': password })

  const basicAuth = 'Basic ' + window.btoa(website.formLoginClient)

  // 保存当前选中的 basic 认证信息
  setStore({
    name: 'basicAuth',
    content: basicAuth,
    type: 'session'
  })

  return request({
    url: '/auth/oauth2/token',
    headers: {
      isToken: false,
      Authorization: basicAuth
    },
    method: 'post',
    params: { randomStr, code, grant_type, scope },
    data: dataObj
  })
}

export const loginByMobile = (smsForm) => {
  const grant_type = 'app'

  const basicAuth = 'Basic ' + window.btoa(website.smsLoginClient)

  // 保存当前选中的 basic 认证信息
  setStore({
    name: 'basicAuth',
    content: basicAuth,
    type: 'session'
  })

  return request({
    url: '/auth/oauth2/token',
    headers: {
      isToken: false,
      'Authorization': basicAuth
    },
    method: 'post',
    params: { phone: smsForm.phone, code: smsForm.code, grant_type, scope }
  })
}

export const ssoLogin = (ticket,service) => {
  return request({
    url: '/auth/token/sso_login_get_token',
    method: 'get',
    params: { ticket, service }
  })
}

export const refreshToken = refresh_token => {
  const grant_type = 'refresh_token'
  // 获取当前选中的 basic 认证信息
  const basicAuth = getStore({ name: 'basicAuth' })

  return request({
    url: '/auth/oauth2/token',
    headers: {
      isToken: false,
      Authorization: basicAuth
    },
    method: 'post',
    params: { refresh_token, grant_type, scope }
  })
}

export const getUserInfo = () => {
  return request({
    url: '/admin/user/info',
    method: 'get'
  })
}

export const logout = () => {
  return request({
    url: '/auth/token/logout',
    method: 'delete'
  })
}

/**
 * 校验令牌，若有效期小于半小时自动续期
 * 
 * 定时任务请求后端接口返回实际的有效时间，不进行本地计算避免 客户端和服务器机器时钟不一致
 * @param refreshLock
 */
export const checkToken = (refreshLock, $store) => {
  const token = store.getters.access_token
  // 获取当前选中的 basic 认证信息
  const basicAuth = getStore({ name: 'basicAuth' })

  if (validatenull(token) || validatenull(basicAuth)) {
    return
  }

  request({
    url: '/auth/token/check_token',
    headers: {
      isToken: false,
      Authorization: basicAuth
    },
    method: 'get',
    params: { token }
  }).then(response => {
    const expire = response && response.data && response.data.exp
    if (expire) {
      const expiredPeriod = expire * 1000 - new Date().getTime()
      console.log('当前token过期时间', expiredPeriod, '毫秒')
      //小于半小时自动续约
      if (expiredPeriod <= website.remainingTime) {
        if (!refreshLock) {
          refreshLock = true
          $store.dispatch('RefreshToken')
            .catch(() => {
              clearInterval(this.refreshTime)
            })
          refreshLock = false
        }
      }
    }
  }).catch(error => {
    console.error(error)
  })
}

/**
 * 注册用户
 */
export const registerUser = (userInfo) => {
  return request({
    url: '/admin/register/user',
    method: 'post',
    data: userInfo
  })
}


/**
 * 发送短信
 */
export const sendSmsCode = (form) => {
  return request({
    url: '/admin/app/sms',
    method: 'post',
    data: form
  })
}
