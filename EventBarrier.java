
public class EventBarrier extends AbstractEventBarrier{
	
	private int count;
	private boolean signal;
	
	public EventBarrier() {
		signal = false;
		this.count = 0;
	}

	@Override
	public synchronized void arrive() {
		this.count += 1;
		if (signal) {
			return;
		}
		while (!signal) {
			try {
				//System.out.println("waiting after arriving");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized void raise() {
		if (count != 0) {
			signal = true;
		}
		while (signal) {
			notifyAll();
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		signal = false;
	}

	@Override
	public synchronized void complete() {
		this.count -= 1;
		if (this.count == 0) {
			signal = false;
			notifyAll();
		}
	}

	@Override
	public synchronized int waiters() {
		return this.count;
	}

}
