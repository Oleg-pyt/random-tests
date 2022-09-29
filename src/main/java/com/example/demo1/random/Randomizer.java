package com.example.demo1.random;

import java.util.Random;

public class Randomizer {
    private int last;
    private int adapt;
    private long multi;
    private int mod;


    public Randomizer() {
        last = 0;//new Random().nextInt();
    }

    public int getAdapt() {
        return adapt;
    }

    public void setAdapt(int adapt) {
        this.adapt = adapt;
    }

    public long getMulti() {
        return multi;
    }

    public void setMulti(long multi) {
        this.multi = multi;
    }

    public int getMod() {
        return mod;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public void clear() {
        last = new Random().nextInt();
    }

    public int nextInt() {
        return (last = (int) ((adapt + multi * last) % mod));
    }
}
