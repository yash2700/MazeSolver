/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Project.DFS;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author yash
 */
public class view extends JFrame implements ActionListener, MouseListener {
    
    /*
    * Values : 0 = not visited
    *          1 = blocked wall
    *          2 = visited
    *          9 = target block
    */
    
    private int[][] maze = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,1,0,1,0,1,0,0,0,0,0,1},
        {1,0,1,0,0,0,1,0,1,1,1,0,1},
        {1,0,0,0,1,1,1,0,0,0,0,0,1},
        {1,0,1,0,0,0,0,0,1,1,1,0,1},
        {1,0,1,0,1,1,1,0,1,0,0,0,1},
        {1,0,1,0,1,0,0,0,1,1,1,0,1},
        {1,0,1,0,1,1,1,0,1,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,0,1,9,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1}};
    
    private int[] target = {8,11};
    
    JButton submitButton;
    JButton cancelButton;
    JButton clearButton;
    
    private List<Integer> path = new ArrayList<Integer>();
    
    public view(){
        this.setTitle("Maze Solver");
        this.setSize(520,500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setBounds(120,400,80,30);
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        cancelButton.setBounds(200,400,80,30);
        
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        clearButton.setBounds(280,400,80,30);
        
        this.addMouseListener(this);
        this.add(submitButton);
        this.add(cancelButton);
        this.add(clearButton);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==submitButton){
            try{
                DFS.searchPath(maze,1,1, path);
                this.repaint();
            }
            catch(Exception excp){
                JOptionPane.showMessageDialog(null, excp.toString());
            }
        }
        if(e.getSource()==cancelButton){
            int flag = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to exit", "Submit", 
                    JOptionPane.YES_NO_OPTION);
            if(flag == 0){
                System.exit(0);
            }
        }
        if(e.getSource()==clearButton){
            path.clear();
            for(int row=0; row<maze.length; row++){
                for(int col=0; col<maze[0].length; col++){
                    if(maze[row][col]==2){
                        maze[row][col] = 0;
                    }
                }
            }
            this.repaint();
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getX()>=0 && e.getX()<=520 && e.getY()>=0 && e.getY()<=440){
            int x = e.getY()/40;
            int y = (e.getX())/40;
            if(maze[x][y]==1){
                return;
            }
            Graphics g = getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(40*target[1], 40*target[0], 40, 40); 
            g.setColor(Color.RED);
            maze[target[0]][target[1]]=0;
            maze[x][y] = 9;
            g.fillRect((40*y), 40*x, 40, 40);
            target[0] = x;
            target[1] = y;
        }
    }   
    
    @Override
    public void mouseEntered(MouseEvent e){
        
    }
    
    @Override
    public void mouseExited(MouseEvent e){
        
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        for(int row=0; row<maze.length; row++){
            for(int col=0; col<maze[0].length; col++){
                Color color;
                switch(maze[row][col]){
                    case 1 : color = Color.BLACK; break;
                    case 9 : color = Color.RED; break;
                    default : color = Color.WHITE;
                }
                
                g.setColor(color);
                g.fillRect(40*col, 40*row, 40, 40);
                g.setColor(Color.BLACK);
                g.drawRect(40*col, 40*row, 40, 40);
                
            }
        }
        
        for(int p=0; p<path.size(); p+=2){
            int pathX = path.get(p);
            int pathY = path.get(p+1);
            g.setColor(Color.GREEN);
            g.fillRect(40*pathY, 40*pathX, 40, 40);
        }
    }
    
    public static void main(String[] args){
        view view1 = new view();
        view1.setVisible(true);
    }
}
