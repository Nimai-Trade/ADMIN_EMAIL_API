package com.nimai.kyc.util;

import java.util.concurrent.atomic.AtomicLong;

import com.nimai.kyc.repository.SequenceGenerator;

public class AtomicSequenceGenerator implements SequenceGenerator {

    private AtomicLong value = new AtomicLong(1);

    @Override
    public long getNext() {
        return value.getAndIncrement();
    }
    
    public static void main(String[] args) {
    	AtomicSequenceGenerator cm=new AtomicSequenceGenerator();
    	System.out.println(cm.getNext());
	}

}
