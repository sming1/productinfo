package com.yiyinmei.store.beans;

public class SalaryInfo {
   public SalaryInfo(int mId, String name, String store, String salary,String event) {
      this.mId = mId;
      this.mName = name;
      this.mNumber = store;
      this.mSalary = salary;
      this.event = event;
   }

   public int getmId() {
      return mId;
   }

   public void setmId(int mId) {
      this.mId = mId;
   }

   public String getmName() {
      return mName;
   }

   public void setmName(String mName) {
      this.mName = mName;
   }

   public String getmNumber() {
      return mNumber;
   }

   public void setmNumber(String mNumber) {
      this.mNumber = mNumber;
   }

   public String getmSalary() {
      return mSalary;
   }

   public void setmSalary(String mSalary) {
      this.mSalary = mSalary;
   }

   public String getEvent() {
      return event;
   }

   public void setEvent(String event) {
      this.event = event;
   }

   private int mId;
   private String mName;
   private String mNumber;
   private String mSalary;
   private String event;

}
