import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class JavaSwingDragDropExample {
    private DrawingTrain _train;

    public JavaSwingDragDropExample() {
        JFrame frame = new JFrame("Пример Drag-and-Drop в Java Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Создаем JLabel для перетаскивания
        JLabel labelTrain = new JLabel("labelTrain");
        labelTrain.setBounds(100,100,100,100);
        labelTrain.setTransferHandler(new LabelTransferHandler());
        labelTrain.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                labelTrain.getTransferHandler().exportAsDrag(labelTrain, e, TransferHandler.COPY);
            }
        });

        // Создаем JPanel для приема перетаскиваемых данных
        JPanel panelObject = new JPanel();
        panelObject.setBounds(0,0,100,100);
        panelObject.setTransferHandler(new PanelTransferHandler());
        panelObject.setBackground(Color.BLACK);

        frame.add(labelTrain);
        frame.add(panelObject);

        frame.setSize(1000,1000);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JavaSwingDragDropExample());
    }

    class LabelTransferHandler extends TransferHandler {
        @Override
        public int getSourceActions(JComponent c) {
            return TransferHandler.COPY;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            return new StringSelection(((JLabel)c).getText());
        }
    }

    class PanelTransferHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            if (canImport(support)) {
                try {
                    String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    switch (data) {
                        case "labelTrain":
                            _train = new DrawingTrain();
                            break;
                        case "labelLoco":
                            //_train = new DrawingLoco();
                            break;
                    }
                    // Выполните вашу определенную функцию здесь
                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }
}

class DrawingTrain {
    // Реализация DrawingTrain
}

class DrawingLoco {
    // Реализация DrawingLoco
}
