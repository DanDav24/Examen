package psnexamen;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danie
 */
public class Entry {
    public String username;
    public long position;
    public Entry next;

    public Entry(String username, long position) {
        this.username = username;
        this.position = position;
        this.next = null;
    }
    
}
