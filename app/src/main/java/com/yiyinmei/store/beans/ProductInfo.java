package com.yiyinmei.store.beans;

public class ProductInfo {
   public ProductInfo(int mId, String name, int store, String event) {
      this.mId = mId;
      this.name = name;
      this.store = store;
      this.event = event;
   }

   private int mId;
   private String name;
   private int store;
   private String event;

   public int getmId() {
      return mId;
   }

   public void setmId(int mId) {
      this.mId = mId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getStore() {
      return store;
   }

   public void setStore(int store) {
      this.store = store;
   }

   public String getEvent() {
      return event;
   }

   public void setEvent(String event) {
      this.event = event;
   }
}
