package com.yangwei.order.redlock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author 杨威
 */
public interface DistributedLocker {

    /**
     * 获取锁，拿不到lock就不罢休，不然线程就一直block
     * @param lockKey lockKey
     * @return RLock
     */
    RLock lock(String lockKey);

    /**
     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
     * @param lockKey lockKey
     * @param leaseTime leaseTime为加锁时间，单位为秒
     * @return RLock
     */
    RLock lock(String lockKey, long leaseTime);

    /**
     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
     * @param lockKey lockKey
     * @param unit unit
     * @param leaseTime leaseTime为加锁时间，时间单位由unit确定
     * @return RLock
     */
    RLock lock(String lockKey, TimeUnit unit, long leaseTime);

    /**
     *  尝试获取锁，获取到立即返回true,未获取到立即返回false
     * @param lockKey lockKey
     * @return boolean
     */
    boolean tryLock(String lockKey);

    /**
     * 尝试获取锁，在等待时间内获取到锁则返回true,否则返回false,如果获取到锁，则要么执行完后程序释放锁，要么在给定的超时时间leaseTime后释放锁
     * @param lockKey lockKey
     * @param unit unit
     * @param waitTime waitTime
     * @param leaseTime leaseTime
     * @return boolean
     */
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    /**
     * 释放锁
     * @param lockKey lockKey
     */
    void unlock(String lockKey);

    /**
     * 释放锁
     * @param lock 锁
     */
    void unlock(RLock lock);

    /**
     * 锁是否被任意一个线程锁持有
     * @param lockKey 锁
     * @return boolean
     */
    boolean isLocked(String lockKey);
}
