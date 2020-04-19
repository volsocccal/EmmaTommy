package emmaTommy.DBAbstraction.Actors;

class DBLock {
    protected String lockOwner = ""; 
    protected Boolean locked = false;
    public Boolean isLocked() {
    	return this.locked;
    }
    public String getLockOwner() {
    	return this.lockOwner;
    }
    public void acquireLock(String newLockOwner) {
    	if (newLockOwner == null) {
    		throw new NullPointerException("Received null newLockOwner");
    	}
    	if (newLockOwner.isBlank()) {
    		throw new IllegalArgumentException("Received blanck newLockOwner");
    	}
    	if (locked) {
    		if (lockOwner.compareTo(newLockOwner) != 0) {
    			throw new RuntimeException(newLockOwner + " tried to acquire lock, but it was already owned by " + lockOwner); 
    		}
    	} else {
    		locked = true;
    		lockOwner = newLockOwner;
    	}
    }
    public void releaseLock(String releasingLockOwner) {
    	if (releasingLockOwner == null) {
    		throw new NullPointerException("Received null releasingLockOwner");
    	}
    	if (releasingLockOwner.isBlank()) {
    		throw new IllegalArgumentException("Received blanck releasingLockOwner");
    	}
    	if (locked) {
    		if (lockOwner.compareTo(releasingLockOwner) != 0) {
    			throw new RuntimeException(releasingLockOwner + " tried to release lock, but it was owned by " + lockOwner); 
    		} else {
    			this.locked = false;
        		this.lockOwner = "";
    		}
    	} else {
    		this.locked = false;
    		this.lockOwner = "";
    	}
    }
    public void forceLockRelease() {
    	this.locked = false;
		this.lockOwner = "";
    }
 }
