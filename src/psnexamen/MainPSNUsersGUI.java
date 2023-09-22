package psnexamen;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danie
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
public class MainPSNUsersGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("PSN Users");

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(2, 2));

                JButton addUserButton = new JButton("Agregar Usuario");
                JButton deactivateUserButton = new JButton("Desactivar Usuario");
                JButton playerInfoButton = new JButton("Información del Jugador");
                JButton addTrophyButton = new JButton("Agregar Trofeo");

                panel.add(addUserButton);
                panel.add(deactivateUserButton);
                panel.add(playerInfoButton);
                panel.add(addTrophyButton);

                frame.add(panel, BorderLayout.CENTER);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 200);
                frame.setVisible(true);

                // Crear una instancia de PSNUsers
                PSNUsers psnUsers = new PSNUsers();

                addUserButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Agregar lógica para mostrar la ventana de agregar usuario
                        // y llamar a psnUsers.addUser(username) con el nombre de usuario ingresado
                        String username = JOptionPane.showInputDialog(frame, "Ingrese el nombre de usuario:");
                        if (username != null) {
                            psnUsers.addUser(username);
                        }
                    }
                });

                deactivateUserButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Agregar lógica para mostrar la ventana de desactivar usuario
                        // y llamar a psnUsers.deactivateUser(username) con el nombre de usuario ingresado
                        String username = JOptionPane.showInputDialog(frame, "Ingrese el nombre de usuario a desactivar:");
                        if (username != null) {
                            psnUsers.deactivateUser(username);
                        }
                    }
                });

                playerInfoButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Agregar lógica para mostrar la ventana de información del jugador
                        // y llamar a psnUsers.getPlayerInfo(username) con el nombre de usuario ingresado
                        String username = JOptionPane.showInputDialog(frame, "Ingrese el nombre de usuario:");
                        if (username != null) {
                            psnUsers.getPlayerInfo(username);
                        }
                    }
                });

                addTrophyButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Agregar lógica para mostrar la ventana de agregar trofeo
                        // y llamar a psnUsers.addTrophyTo(username, trophyGame, trophyName, type)
                        // con los datos ingresados
                        String username = JOptionPane.showInputDialog(frame, "Ingrese el nombre de usuario:");
                        if (username != null) {
                            String trophyGame = JOptionPane.showInputDialog(frame, "Ingrese el nombre del juego:");
                            String trophyName = JOptionPane.showInputDialog(frame, "Ingrese el nombre del trofeo:");
                            Trophy type = Trophy.PLATINO; // Puedes cambiar esto según el tipo de trofeo
                            psnUsers.addTrophyTo(username, trophyGame, trophyName, type);
                        }
                    }
                });
            }
        });
    }
}