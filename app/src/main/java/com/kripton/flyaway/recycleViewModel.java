package com.kripton.flyaway;

public class recycleViewModel {
    vaccineAdapterClass adapterClass;

    public vaccineAdapterClass getAdapterClass() {
        return adapterClass;
    }

    public void setAdapterClass(vaccineAdapterClass adapterClass) {
        this.adapterClass = adapterClass;
    }

    public recycleViewModel(vaccineAdapterClass adapterClass) {
        this.adapterClass = adapterClass;
    }
}
