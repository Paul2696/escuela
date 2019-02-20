import org.omg.SendingContext.RunTime;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UI extends JFrame implements MapListener{
    JSlider slider;
    JLabel label;
    JButton setKibusBtn;
    JButton setHouseBtn;
    JButton initBtn;
    JPanel mapPanel = new JPanel();
    AddIconListener iconListener = new AddIconListener();
    static final Map map = new Map();
    static final Kibus kibus = new Kibus();
    JButton[][] buttons = new JButton[map.getDimensionX()][map.getDimensionY()];

    public UI(){
        super("Kibus World");
        label = new JLabel("percentage of obstacles: ");
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        slider = new JSlider(0, 100, 10);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                label.setText("percentage of obstacles: " + slider.getValue() + "%");
                map.setObstacles(slider.getValue());
            }
        });
        Dimension d = slider.getPreferredSize();
        slider.setPreferredSize(new Dimension(d.width+100,d.height));
        setKibusBtn = new JButton("Set Kibus");
        setHouseBtn = new JButton("Set House");
        initBtn = new JButton("Go!");
        kibus.setMap(map);
        initBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            kibus.startSearchingHouse();
                        } catch (Exception e) {
                            label.setText("Add Kibus and his house first");
                        }
                    }
                };
                t.start();
            }
        });
        setKibusBtn.addActionListener(iconListener);
        setHouseBtn.addActionListener(iconListener);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());
        rightPanel.add(setKibusBtn);
        rightPanel.add(setHouseBtn);
        rightPanel.add(slider);
        rightPanel.add(initBtn);
        mapPanel.setLayout(new GridLayout(15, 15));
        initMap();
        update();
        add(mapPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(label, BorderLayout.NORTH);
        pack();
        map.setListener(this);
        setVisible(true);
    }

    private void initMap() {
        int counter = 0;
        for(int i =0; i < buttons.length; i++) {
            for(int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(iconListener);
                buttons[i][j].setActionCommand(Integer.toString(counter));
                mapPanel.add(buttons[i][j]);
                counter++;
            }
        }
    }
    public void update(){
            try {
                for (int i = 0; i < map.getDimensionX(); i++) {
                    for (int j = 0; j < map.getDimensionY(); j++) {
                        int coordinate = map.get(i, j);
                        JButton button = buttons[i][j];
                        button.setIcon(null);
                        Image image;
                        switch (coordinate) {
                            case Map.OBSTACLE:
                                image = ImageIO.read(getClass().getResource("Obstacle.png"));
                                button.setIcon(new ImageIcon(image));
                                break;

                            case Map.KIBUS:
                                image = ImageIO.read(getClass().getResource("Link.png"));
                                button.setIcon(new ImageIcon(image));
                                break;

                            case Map.HOUSE:
                                image = ImageIO.read(getClass().getResource("Zelda.png"));
                                button.setIcon(new ImageIcon(image));
                                break;

                            case Map.FREE:
                                break;

                        }
                    }
                }
            }catch(Exception e){
                throw new RuntimeException(e);
            }
    }

    @Override
    public void mapChanged(boolean withDelay){
        update();
        if(withDelay){
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    static class AddIconListener implements ActionListener {
        boolean settingKibus = false;
        boolean settingHouse = false;
        JButton actualKibus = null;
        JButton actualHouse = null;

        public void actionPerformed(ActionEvent event){
            JButton button = (JButton)event.getSource();
            String name = button.getText();
            if(name.contains("Kibus") && !settingKibus){
                settingHouse = false;
                settingKibus = true;
            }
            else if(name.contains("House") && !settingHouse){
                settingKibus = false;
                settingHouse = true;
            }
            else{
                try{
                    int position = Integer.parseInt(event.getActionCommand());
                    if(settingKibus){
                        Coord coord = map.set(position, Map.KIBUS, false);
                        kibus.setActualCoordinate(coord);
                        if(actualKibus != null){
                            position = Integer.parseInt(actualKibus.getActionCommand());
                            map.set(position, Map.FREE, false);
                        }
                        actualKibus = button;
                        settingKibus = false;

                    }
                    else if(settingHouse) {
                        Coord coord = map.set(position, Map.HOUSE, false);
                        kibus.setHouseCoordinate(coord);
                        if(actualHouse != null){
                            position = Integer.parseInt(actualHouse.getActionCommand());
                            map.set(position, Map.FREE, false);
                        }
                        actualHouse = button;
                        settingHouse = false;
                    }
                }catch(Exception e){
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static void main(String args[]) throws Exception{
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UI();
            }
        });
    }
}
