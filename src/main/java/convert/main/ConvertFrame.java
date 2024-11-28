package convert.main;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.aspose.cells.Workbook;

public class ConvertFrame {
	
	String fileName = "";
	File[] selectedFiles;
	
	public ConvertFrame() {
			    
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CSV to JSON");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 500);

            JPanel panel = new JPanel(new GridLayout(4, 0));
            JButton selectFileButton = new JButton("CSV 파일 선택");
            JButton convertButton = new JButton("JSON으로 변환");
            JLabel statusLabel = new JLabel("상태: 대기", SwingConstants.CENTER);

            selectedFiles = new File[0];

            selectFileButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setMultiSelectionEnabled(true);

                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFiles = fileChooser.getSelectedFiles(); 
                    StringBuilder selectedFileNames = new StringBuilder("선택된 파일: ");
                    for (File file : selectedFiles) {
                        selectedFileNames.append(file.getName()).append(", ");
                    }
                    statusLabel.setText(selectedFileNames.toString());
                }
            });

            convertButton.addActionListener(e -> {
                if (selectedFiles.length == 0) {
                    JOptionPane.showMessageDialog(frame, "CSV파일 먼저 업로드하세요", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for (File selectedFile : selectedFiles) {
                    try {
                        Workbook workbook = new Workbook(selectedFile.getAbsolutePath());
                        String outputPath = selectedFile.getParent() + "/" + selectedFile.getName() + ".json";
                        workbook.save(outputPath);
                        statusLabel.setText("변환 성공: " + outputPath);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        statusLabel.setText("Error: " + ex.getMessage());
                    }
                }
            });

            panel.add(selectFileButton);
            panel.add(convertButton);
            panel.add(statusLabel);

            frame.add(panel);
            frame.setVisible(true);
        });
		
	}
}
