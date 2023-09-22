package psnexamen;


import psnexamen.HashTable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danie
 */
public class PSNUsers {
    private RandomAccessFile raf;
    private HashTable users;

    public PSNUsers() {
        try {
            File psnFile = new File("psn");
            if (!psnFile.exists() || psnFile.length() == 0) {
                System.out.println("El archivo 'psn' no existe o está vacío. Debe crearlo con datos válidos.");
                // Puedes manejar este caso de otra manera según tus necesidades
                return;
            }

            this.raf = new RandomAccessFile(psnFile, "rw");
            this.users = new HashTable();
            reloadHashTable();
        } catch (IOException e) {
            handleException("Error al abrir el archivo psn.", e);
        }
    }

    private void reloadHashTable() {
        try {
            raf.seek(0);
            users = new HashTable();

            while (raf.getFilePointer() < raf.length()) {
                String username = raf.readUTF();
                long position = raf.readLong();
                boolean activated = raf.readBoolean();

                if (activated) {
                    users.add(username, position);
                }
            }
        } catch (EOFException e) {
            System.err.println("El archivo psn está vacío o no contiene datos válidos.");
            // Maneja este caso según tus necesidades
        } catch (IOException e) {
            handleException("Error al recargar la tabla hash desde el archivo psn.", e);
        }
    }

    public void addUser(String username) {
        try {
            if (users.search(username) != -1) {
                System.out.println("El usuario ya existe.");
                return;
            }
            raf.seek(raf.length());
            raf.writeUTF(username);
            raf.writeLong(raf.getFilePointer());
            raf.writeBoolean(true);
            users.add(username, raf.getFilePointer());
            System.out.println("Usuario agregado con éxito.");
        } catch (IOException e) {
            handleException("Error al agregar un usuario.", e);
        }
    }

    public void deactivateUser(String username) {
        try {
            long position = users.search(username);
            if (position != -1) {
                raf.seek(position);
                raf.writeBoolean(false);
                users.remove(username);
                System.out.println("Usuario desactivado con éxito.");
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (IOException e) {
            handleException("Error al desactivar un usuario.", e);
        }
    }

    public void addTrophyTo(String username, String trophyGame, String trophyName, Trophy type) {
        try {
            long position = users.search(username);
            if (position != -1) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDate = dateFormat.format(new Date());
                RandomAccessFile trophiesRaf = new RandomAccessFile("trophies.psn", "rw");
                trophiesRaf.seek(trophiesRaf.length());
                trophiesRaf.writeUTF(username);
                trophiesRaf.writeUTF(type.toString());
                trophiesRaf.writeUTF(trophyGame);
                trophiesRaf.writeUTF(trophyName);
                trophiesRaf.writeUTF(currentDate);
                trophiesRaf.close();
                System.out.println("Trofeo agregado con éxito.");
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (IOException e) {
            handleException("Error al agregar un trofeo.", e);
        }
    }
    
    

    public void getPlayerInfo(String username) {
        try {
            long position = users.search(username);
            if (position != -1) {
                raf.seek(position);
                String storedUsername = raf.readUTF();
                raf.readLong();
                boolean activated = raf.readBoolean();
                if (activated) {
                    System.out.println("Información del usuario:");
                    System.out.println("Username: " + storedUsername);

                    RandomAccessFile trophiesRaf = new RandomAccessFile("trophies.psn", "rw");
                    trophiesRaf.seek(0);
                    while (trophiesRaf.getFilePointer() < trophiesRaf.length()) {
                        String trophyUsername = trophiesRaf.readUTF();
                        if (trophyUsername.equals(username)) {
                            String trophyType = trophiesRaf.readUTF();
                            String trophyGame = trophiesRaf.readUTF();
                            String trophyDescription = trophiesRaf.readUTF();
                            String trophyDate = trophiesRaf.readUTF();

                            System.out.println("\nFecha: " + trophyDate);
                            System.out.println("Tipo: " + trophyType);
                            System.out.println("Juego: " + trophyGame);
                            System.out.println("Descripción: " + trophyDescription + "\n");
                        } else {
                            trophiesRaf.readUTF();
                            trophiesRaf.readUTF();
                            trophiesRaf.readUTF();
                            trophiesRaf.readUTF();
                        }
                    }
                    trophiesRaf.close();
                } else {
                    System.out.println("El usuario está desactivado (borrado).");
                }
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (IOException e) {
            handleException("Error al obtener información del usuario.", e);
        }
    }

    private void handleException(String message, Exception e) {
        System.err.println(message + "\nDetalles del error: " + e.getMessage());
        e.printStackTrace();
    }

    public void close() {
        try {
            raf.close();
        } catch (IOException e) {
            handleException("Error al cerrar el archivo psn.", e);
        }
    }
}

    
    

