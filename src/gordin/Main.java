package gordin;

import gordin.gui.model.Model;
import gordin.gui.viewer.Viewer;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/16/13
 * Time: 6:57 PM
 * To change this template use File | Settings | File Templates.
 */
class GUIThread implements Runnable {
    public void run()
    {
        try {
            Model model = new Model("factory.conf");
            Viewer viewer = new Viewer(model);
            viewer.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String args[])
    {
        PropertyConfigurator.configure("properties/log4j.properties");
        SwingUtilities.invokeLater(new GUIThread());
    }
}
