package com.epam.esm.veiw.exception;

public class GiftCertificateNotFound extends RuntimeException{
     private long giftId;

     public GiftCertificateNotFound (long id){
         this.giftId = id;
     }

    public long getGiftId() {
        return giftId;
    }
}
