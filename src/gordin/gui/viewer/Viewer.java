package gordin.gui.viewer;

import gordin.factory.accessorysupply.AccessorySupplier;
import gordin.factory.bodysupply.BodySupplier;
import gordin.factory.carassembly.Worker;
import gordin.factory.dealer.Dealer;
import gordin.factory.enginesupply.EngineSupplier;
import gordin.gui.model.Model;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/22/13
 * Time: 1:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class Viewer {
    private Model model;
    private JFrame window;
    private JPanel slidersPanel;
    private JPanel infoPanel;
    private final JButton start;
    private final JButton stop;
    private JSlider bodySlider;
    private JSlider engineSlider;
    private JSlider accessorySlider;
    private JSlider workerSlider;
    private JSlider dealerSlider;
    private final JLabel carStorageSize;
    private final JLabel bodyStorageSize;
    private final JLabel engineStorageSize;
    private final JLabel accessoryStorageSize;
    private final JLabel numberOfCars;
    private final JLabel numberOfBodies;
    private final JLabel numberOfEngines;
    private final JLabel numberOfAccessories;
    private Timer infoTimer;

    private Viewer()
    {
        carStorageSize = new JLabel("Car storage size: ");
        bodyStorageSize = new JLabel("Body storage size: ");
        engineStorageSize = new JLabel("Engine storage size: ");
        accessoryStorageSize = new JLabel("Accessory storage size: ");
        numberOfCars = new JLabel("Number of released cars: ");
        numberOfBodies = new JLabel("Number of released bodies: ");
        numberOfEngines = new JLabel("Number of released engines: ");
        numberOfAccessories = new JLabel("Number of released accessories: ");

        window = new JFrame();
        slidersPanel = new JPanel();
        infoPanel = new JPanel();
        int min = 0;
        int max = 100;
        int value = 10;
        workerSlider = new JSlider(min, max, value);
        workerSlider.setMajorTickSpacing(20);
        workerSlider.setMinorTickSpacing(2);
        workerSlider.setPaintTicks(true);
        workerSlider.setPaintLabels(true);

        dealerSlider = new JSlider(min, max, value);
        dealerSlider.setMajorTickSpacing(20);
        dealerSlider.setMinorTickSpacing(2);
        dealerSlider.setPaintTicks(true);
        dealerSlider.setPaintLabels(true);

        bodySlider = new JSlider(min, max, value);
        bodySlider.setMajorTickSpacing(20);
        bodySlider.setMinorTickSpacing(2);
        bodySlider.setPaintTicks(true);
        bodySlider.setPaintLabels(true);

        engineSlider = new JSlider(min, max, value);
        engineSlider.setMajorTickSpacing(20);
        engineSlider.setMinorTickSpacing(2);
        engineSlider.setPaintTicks(true);
        engineSlider.setPaintLabels(true);

        accessorySlider = new JSlider(min, max, value);
        accessorySlider.setMajorTickSpacing(20);
        accessorySlider.setMinorTickSpacing(2);
        accessorySlider.setPaintTicks(true);
        accessorySlider.setPaintLabels(true);

        AccessorySupplier.setWorkTime(value*1000);
        EngineSupplier.setWorkTime(value*1000);
        BodySupplier.setWorkTime(value*1000);
        Worker.setWorkTime(value*1000);
        Dealer.setWorkTime(value*1000);

        slidersPanel.setLayout(new GridLayout(10, 1));
        slidersPanel.add(new JLabel("Body speed"));
        slidersPanel.add(bodySlider);
        slidersPanel.add(new JLabel("Engine speed"));
        slidersPanel.add(engineSlider);
        slidersPanel.add(new JLabel("Accessory speed"));
        slidersPanel.add(accessorySlider);
        slidersPanel.add(new JLabel("Work speed"));
        slidersPanel.add(workerSlider);
        slidersPanel.add(new JLabel("Dealer speed"));
        slidersPanel.add(dealerSlider);

        infoPanel.setLayout(new GridLayout(8, 1));
        infoPanel.add(carStorageSize);
        infoPanel.add(bodyStorageSize);
        infoPanel.add(engineStorageSize);
        infoPanel.add(accessoryStorageSize);
        infoPanel.add(numberOfCars);
        infoPanel.add(numberOfBodies);
        infoPanel.add(numberOfEngines);
        infoPanel.add(numberOfAccessories);

        start = new JButton("start");
        stop = new JButton("stop");
        stop.setEnabled(false);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(start);
        buttonPanel.add(stop);

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout(1, 2, 20, 20));
        centralPanel.add(slidersPanel);
        centralPanel.add(infoPanel);
        window.getContentPane().add("Center", centralPanel);
        window.getContentPane().add("South", buttonPanel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(600, 600);
        window.setVisible(true);
    }

    public Viewer(final Model model)
    {
        this();
        this.model = model;
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                int value = source.getValue();
                if(source.equals(bodySlider))
                {
                    model.setBodyProduceSpeed(value*1000);
                }
                else if(source.equals(engineSlider))
                {
                    model.setEngineProduceSpeed(value*1000);
                }
                else if(source.equals(accessorySlider))
                {
                    model.setAccessoryProduceSpeed(value*1000);
                }
                else if(source.equals(workerSlider))
                {
                    model.setWorkerSpeed(value*1000);
                }
                else if(source.equals(dealerSlider))
                {
                    model.setDealerSpeed(value*1000);
                }
            }
        };

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setEnabled(false);
                stop.setEnabled(true);
                model.start();
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                    start.setEnabled(true);
                    stop.setEnabled(false);
                    model.stop();
            }
        });

        bodySlider.addChangeListener(changeListener);
        engineSlider.addChangeListener(changeListener);
        accessorySlider.addChangeListener(changeListener);
        workerSlider.addChangeListener(changeListener);
        dealerSlider.addChangeListener(changeListener);

        infoTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberOfCars.setText("Number of released cars: "+ Worker.producedCars());
                numberOfBodies.setText("Number of released bodies: "+ BodySupplier.producedBodies());
                numberOfEngines.setText("Number of released engines: "+ EngineSupplier.producedEngines());
                numberOfAccessories.setText("Number of released accessories: "+ AccessorySupplier.producedAccessories());

                carStorageSize.setText("Car storage size: "+model.getCarStorageSize());
                bodyStorageSize.setText("Body storage size: "+model.getBodyStorageSize());
                engineStorageSize.setText("Engine storage size: "+model.getEngineStorageSize());
                accessoryStorageSize.setText("Accessory storage size: "+model.getAccessoryStorageSize());

                window.revalidate();
                window.repaint();
            }
        });

        infoTimer.start();
    }

    public void run()
    {
        try {
            window.revalidate();
            window.repaint();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
