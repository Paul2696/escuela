import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class UI extends JFrame{
    JSlider fearSlider;
    JSlider regretSlider;
    JSlider revengeSlider;
    JSlider rejectSlider;
    JSlider hateSlider;
    JSlider willSlider;
    JSlider forgiveSlider;
    JLabel imageLabel;
    JButton initBtn;
    JPanel mainPanel = new JPanel();
    JProgressBar calculusBar = new JProgressBar(0, 100);

    public UI(){
        super("Imaginary Domain Result");
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Setting Sliders
        fearSlider = new JSlider(0, 100, 0);
        fearSlider.setMajorTickSpacing(10);
        fearSlider.setPaintTicks(false);
        fearSlider.setPaintLabels(false);
        fearSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                //label.setText("percentage of obstacles: " + fearSlider.getValue() + "%");
                //map.setObstacles(fearSlider.getValue());
            }
        });
        regretSlider = new JSlider(0, 100, 0);
        regretSlider.setMajorTickSpacing(10);
        regretSlider.setPaintLabels(false);
        regretSlider.setPaintTicks(false);
        regretSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
               // map.setMountains(regretSlider.getValue());
            }
        });
        revengeSlider = new JSlider(0, 100, 0);
        revengeSlider.setMajorTickSpacing(10);
        revengeSlider.setPaintLabels(false);
        revengeSlider.setPaintTicks(false);
        revengeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                //map.setRivers(revengeSlider.getValue());
            }
        });
        rejectSlider = new JSlider(0, 100, 0);
        rejectSlider.setMajorTickSpacing(10);
        rejectSlider.setPaintTicks(false);
        rejectSlider.setPaintLabels(false);
        rejectSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                //map.setPlain(rejectSlider.getValue());
            }
        });
        hateSlider = new JSlider(0, 100, 0);
        hateSlider.setMajorTickSpacing(10);
        hateSlider.setPaintTicks(false);
        hateSlider.setPaintLabels(false);
        hateSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                //map.setRavine(hateSlider.getValue());
            }
        });
        willSlider = new JSlider(0, 100, 0);
        willSlider.setMajorTickSpacing(10);
        willSlider.setPaintTicks(false);
        willSlider.setPaintLabels(false);
        willSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {

            }
        });
        forgiveSlider = new JSlider(0, 100, 0);
        forgiveSlider.setMajorTickSpacing(10);
        forgiveSlider.setPaintTicks(false);
        forgiveSlider.setPaintLabels(false);
        forgiveSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {

            }
        });

        Dimension d = fearSlider.getPreferredSize();
        fearSlider.setPreferredSize(new Dimension(d.width+100,d.height));
        regretSlider.setPreferredSize(new Dimension(d.width+100,d.height));
        revengeSlider.setPreferredSize(new Dimension(d.width+100,d.height));
        rejectSlider.setPreferredSize(new Dimension(d.width+100,d.height));
        hateSlider.setPreferredSize(new Dimension(d.width+100,d.height));
        willSlider.setPreferredSize(new Dimension(d.width+100,d.height));
        forgiveSlider.setPreferredSize(new Dimension(d.width+100,d.height));

        //Setting buttons
        initBtn = new JButton("Start Imaginary Domain Calculus");
        initBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Thread t;
                    t = new Thread() {
                        public void run(){
                            String result = Canaan.inferAfterlife(Canaan.fuzzyFear(fearSlider.getValue()),
                                                  Canaan.fuzzyRegret(regretSlider.getValue()),
                                                  Canaan.fuzzyRevenge(revengeSlider.getValue()),
                                                  Canaan.fuzzyReject(rejectSlider.getValue()),
                                                  Canaan.fuzzyHate(hateSlider.getValue()),
                                                  Canaan.fuzzyWill(willSlider.getValue()),
                                                  Canaan.fuzzyForgiveness(forgiveSlider.getValue()));
                            update(result);
                        }
                    };
                t.start();

            }
        });

        //Setting Panels
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(7, 0));
        leftPanel.setLayout(new GridLayout(8, 0));
        leftPanel.add(new JLabel("Fear"));
        leftPanel.add(fearSlider);
        leftPanel.add(new JLabel("Regret"));
        leftPanel.add(regretSlider);
        leftPanel.add(new JLabel("Revenge"));
        leftPanel.add(revengeSlider);
        leftPanel.add(new JLabel("Reject"));
        leftPanel.add(rejectSlider);
        rightPanel.add(new JLabel("Hate"));
        rightPanel.add(hateSlider);
        rightPanel.add(new JLabel("Will"));
        rightPanel.add(willSlider);
        rightPanel.add(new JLabel("Forgiveness"));
        rightPanel.add(forgiveSlider);
        rightPanel.add(initBtn);
        calculusBar.setStringPainted(true);
        mainPanel.setLayout(new GridLayout(1, 1));
        try{
            Image image;
            ImageIcon io;
            image = ImageIO.read(getClass().getResource("xenosaga.jpg"));
            io = new ImageIcon(image);
            imageLabel = new JLabel();
            imageLabel.setIcon(io);
            mainPanel.add(imageLabel);
        }catch(IOException e){
            e.printStackTrace();
        }
        add(mainPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(leftPanel, BorderLayout.WEST);
        pack();
        setVisible(true);
    }

    public void update(String result){
        try {
            Image image;
            ImageIcon io;
            if(result.equals("Inconsciente colectivo")){
                image = ImageIO.read(getClass().getResource("colectivo.png"));
                io = new ImageIcon(image);
                imageLabel.setIcon(io);
            }
            else if(result.equals("Gnosis")){
                image = ImageIO.read(getClass().getResource("gnosis.png"));
                io = new ImageIcon(image);
                imageLabel.setIcon(io);
            }
            else if(result.equals("Testamento")){
                image = ImageIO.read(getClass().getResource("testamento.png"));
                io = new ImageIcon(image);
                imageLabel.setIcon(io);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    public static void main(String args[]) throws Exception{
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Slider.paintValue", false);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UI();
            }
        });
    }
}