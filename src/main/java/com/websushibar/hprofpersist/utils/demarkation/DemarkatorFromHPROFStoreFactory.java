package com.websushibar.hprofpersist.utils.demarkation;

import com.websushibar.hprofpersist.store.HPROFMemoryStore;

public class DemarkatorFromHPROFStoreFactory
    implements HPROFDemarkatorFactory {

    private HPROFMemoryStore store;

    public DemarkatorFromHPROFStoreFactory(HPROFMemoryStore store) {

        this.store = store;
    }

    @Override
    public HPROFDemarkator makeDemarkator() {

        return new HPROFDemarkator(store.getByLocationRegister());

    }

    public void setStore(HPROFMemoryStore store) {
        this.store = store;
    }
}
