/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Recoban10
 */
public class Bilangan {
    static int pencacah = 0;
    int nilai;
    
    public Bilangan(int nilai){
        this.nilai = nilai;
        pencacah++;
    }
    
    public void info(){
        System.out.println("Nilai:"+nilai);
        System.out.println("Pencacah:"+pencacah);
        System.out.println("");
    }
}
