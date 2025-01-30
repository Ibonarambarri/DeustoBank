package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class StyledButton {
    // Método público para crear un botón estilizado
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 40));  // Tamaño del botón
        button.setFont(new Font("SansSerif", Font.BOLD, 16));  // Fuente moderna y ligeramente más grande
        button.setForeground(Color.WHITE);  // Color del texto
        button.setBackground(new Color(52, 152, 219));  // Color de fondo elegante
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Márgenes internos del botón

        // Bordes redondeados
        button.setBorder(new RoundedBorder(10));  // Usa una clase de borde redondeado

        // Efecto de sombra suave
        button.setFocusPainted(false);  // Quita el borde de enfoque
        button.setContentAreaFilled(false);  // Desactiva el relleno del área
        button.setOpaque(true);  // Activa el color de fondo
        button.setBorderPainted(false);  // Desactiva la pintura del borde

        // Efecto de hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(41, 128, 185));  // Cambia a un color más oscuro al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(52, 152, 219));  // Color original al salir
            }
        });

        return button;
    }

    // Clase interna para crear un borde redondeado personalizado
    public static class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(c.getForeground());
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
