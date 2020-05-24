package emmaTommy.DBServerAbstraction.Actors;

class DBLock {
    protected String lockOwnerName = ""; 
    protected String lockOwnerID = ""; 
    protected Boolean locked = false;
    public Boolean isLocked() {
    	return this.locked;
    }
    public String getLockOwnerName() {
    	return this.lockOwnerName;
    }
    public String getLockOwnerID() {
    	return this.lockOwnerID;
    }
    public void acquireLock(String newLockOwnerName, String newLockOwnerID) {
    	if (newLockOwnerName == null) {
    		throw new NullPointerException("Received null newLockOwnerName");
    	}
    	if (newLockOwnerName.isBlank()) {
    		throw new IllegalArgumentException("Received blanck newLockOwnerName");
    	}
    	if (newLockOwnerID == null) {
    		throw new NullPointerException("Received null newLockOwnerID");
    	}
    	if (newLockOwnerID.isBlank()) {
    		throw new IllegalArgumentException("Received blanck newLockOwnerID");
    	}
    	if (this.locked) {
    		if (lockOwnerName.compareTo(newLockOwnerName) != 0) {
    			throw new RuntimeException(newLockOwnerName + " tried to acquire lock, but it was already owned by " + lockOwnerName); 
    		} else {
    			if (lockOwnerID.compareTo(newLockOwnerID) != 0) {
    				throw new RuntimeException("Actor with ID " + newLockOwnerID + " tried to acquire lock, but it was already owned by actor with ID " + lockOwnerID);
    			}
    		}
    	} else {
    		this.locked = true;
    		this.lockOwnerName = newLockOwnerName;
    		this.lockOwnerID = newLockOwnerID;
    	}
    }
    public void releaseLock(String releasingLockOwnerName, String releasingLockOwnerID) {
    	if (releasingLockOwnerName == null) {
    		throw new NullPointerException("Received null releasingLockOwnerName");
    	}
    	if (releasingLockOwnerName.isBlank()) {
    		throw new IllegalArgumentException("Received blanck releasingLockOwnerName");
    	}
    	if (releasingLockOwnerID == null) {
    		throw new NullPointerException("Received null releasingLockOwnerID");
    	}
    	if (releasingLockOwnerID.isBlank()) {
    		throw new IllegalArgumentException("Received blanck releasingLockOwnerID");
    	}
    	if (this.locked) {
    		if (lockOwnerName.compareTo(releasingLockOwnerName) != 0) {
    			throw new RuntimeException(releasingLockOwnerName + " tried to release lock, but it was owned by " + lockOwnerName); 
    		} else {
    			if (lockOwnerID.compareTo(releasingLockOwnerID) != 0) {
    				throw new RuntimeException("Actor with ID " + releasingLockOwnerID + " tried to release lock, but it was owned by actor with ID " + lockOwnerID);
    			}
    		}
    		this.locked = false;
    		this.lockOwnerName = "";
    		this.lockOwnerID = "";
    	}
    }
    public void forceLockRelease() {
    	this.locked = false;
		this.lockOwnerName = "";
		this.lockOwnerID = "";
    }
 }
