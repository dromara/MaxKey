package org.maxkey.provision.thread;

public class ProvisioningRunnerThread  extends Thread{

	ProvisioningRunner runner;

	public ProvisioningRunnerThread(ProvisioningRunner runner) {
		super();
		this.runner = runner;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(60 * 1000);
				runner.provisions();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
