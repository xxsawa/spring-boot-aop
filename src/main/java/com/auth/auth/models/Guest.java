package com.auth.auth.models;

public class Guest implements IInteractingClient{
    @Override
    public String getRegion() {
        return "Guest regeion: WEU";
    }
}
