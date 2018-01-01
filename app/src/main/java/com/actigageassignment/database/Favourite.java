package com.actigageassignment.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by santo on 31/12/17.
 */

@Entity
public class Favourite {
    @Id(autoincrement = true)
    private Long id;
    private byte[] logoBytes;

    @Generated(hash = 1288448322)
    public Favourite(Long id, byte[] logoBytes) {
        this.id = id;
        this.logoBytes = logoBytes;
    }

    @Generated(hash = 1933414424)
    public Favourite() {
    }

    public byte[] getLogoBytes() {
        return logoBytes;
    }

    public void setLogoBytes(byte[] logoBytes) {
        this.logoBytes = logoBytes;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
