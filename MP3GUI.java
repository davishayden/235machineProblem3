import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MP3GUI extends JFrame {

	String[] choices = { "None", "Rotational", "Keyed Rotational" };
	Integer[] factors = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
			16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
	JFrame theFrame = new JFrame();
	int key;
	String message;
	JTextArea theTextArea = new JTextArea();
	JTextArea resultTextArea = new JTextArea();
	JComboBox factorBox = new JComboBox(factors);
	JTextField keyInput = new JTextField();

	public MP3GUI() {
		super("MP3");
		makeNoneFrame();
		setVisible(true);

	}

	// Create just a text area and a combobox
	private void makeNoneFrame() {
		Container contentPane = getContentPane();
		contentPane.setMinimumSize(new Dimension(1000, 100));
		contentPane.removeAll();
		theTextArea.setEditable(true);
		theTextArea.setRows(8);
		theTextArea.setColumns(30);

		// Initialize the selector box for switching between gui types
		JComboBox theBox = new JComboBox(choices);
		theBox.setSelectedItem("None");

		contentPane.add(theTextArea, BorderLayout.CENTER);
		contentPane.add(theBox, BorderLayout.SOUTH);
		pack();

		// Listen for the user input to switch
		theBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				String selection = comboBox.getSelectedItem().toString();

				if (selection.equals("Rotational")) {
					makeRotatedFrame();
				}
				if (selection.equals("Keyed Rotational")) {
					makeKeyedFrame();
				}

			}
		});
	}

	// Create the GUI for the Rotated Message
	private void makeRotatedFrame() {

		Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout());

		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());

		JPanel midPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		midPanel.setLayout(layout);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JPanel rotationPanel = new JPanel();
		rotationPanel.setLayout(new FlowLayout());

		theTextArea.setEditable(true);

		resultTextArea.setRows(8);
		resultTextArea.setColumns(30);

		JComboBox theBox = new JComboBox(choices);
		theBox.setSelectedItem("Rotational");

		JLabel rotationLabel = new JLabel("Rotation");

		// Add buttons for the user to use to either encrypt or decrypt the
		// message
		JButton encryptButton = new JButton("Encrypt");
		JButton decryptButton = new JButton("Decrypt");

		// add everything to the panel
		buttonPanel.add(encryptButton);
		buttonPanel.add(decryptButton);

		rotationPanel.add(rotationLabel);
		rotationPanel.add(factorBox);

		northPanel.add(theTextArea, BorderLayout.NORTH);
		northPanel.add(theBox, BorderLayout.CENTER);
		northPanel.add(rotationPanel, BorderLayout.SOUTH);

		southPanel.add(resultTextArea, BorderLayout.CENTER);
		southPanel.add(buttonPanel, BorderLayout.SOUTH);

		content.add(northPanel, BorderLayout.NORTH);
		content.add(southPanel, BorderLayout.SOUTH);

		contentPane.add(content);

		pack();

		// listen to see if the user wishes to switch to a different gui
		theBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				String selection = comboBox.getSelectedItem().toString();
				if (selection.equals("None")) {
					makeNoneFrame();
				}
				if (selection.equals("Keyed Rotational")) {
					makeKeyedFrame();
				}

			}
		});

		// listens for the user to switch the rotation factor which is set to
		// the key
		factorBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				String selection = comboBox.getSelectedItem().toString();
				key = Integer.decode(selection);

			}
		});

		// listens to see if the user presses the encrypt button and then if so
		// calls rotateMessage
		encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				rotateMessage();
			}
		});
		// listens to see if the user presses the decrypt button and then if so
		// calls decryptMessage
		decryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				decryptMessage();
			}
		});
	}

	// method which takes the input from the gui and rotates the message. It
	// then sends the necessary output to the gui
	private void rotateMessage() {
		message = theTextArea.getText();
		RotatedMessage rMessage = new RotatedMessage(message);
		String keyString = factorBox.getSelectedItem().toString();
		key = Integer.decode(keyString);
		rMessage.setRotation(key);
		rMessage.encrypt();
		resultTextArea.setText(rMessage.toString());
	}

	// method which takes the input from the gui and decrypts the message
	// sending it back to the gui.
	private void decryptMessage() {
		message = resultTextArea.getText();
		RotatedMessage rMessage = new RotatedMessage(message);
		rMessage.decrypt();

		theTextArea.setText(rMessage.toString());
		factorBox.setSelectedItem(rMessage.getRotation());
	}

	// This method creates the GUI for the Keyed Message
	private void makeKeyedFrame() {

		Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout());

		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());

		JPanel midPanel = new JPanel();
		midPanel.setLayout(new BorderLayout());

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());

		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new FlowLayout());

		JPanel rotationPanel = new JPanel();
		rotationPanel.setLayout(new FlowLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		theTextArea.setEditable(true);
		JComboBox theBox = new JComboBox(choices);
		JLabel rotationLabel = new JLabel("Rotation");
		theBox.setSelectedItem("Keyed Rotational");
		JLabel keyLabel = new JLabel("Key:");
		keyInput.setColumns(10);
		JButton encryptButton = new JButton("Encrypt");
		JButton decryptButton = new JButton("Decrypt");

		buttonPanel.add(encryptButton);
		buttonPanel.add(decryptButton);

		keyPanel.add(keyLabel);
		keyPanel.add(keyInput);

		rotationPanel.add(rotationLabel);
		rotationPanel.add(factorBox);

		northPanel.add(theTextArea, BorderLayout.NORTH);
		northPanel.add(theBox, BorderLayout.SOUTH);

		midPanel.add(rotationPanel, BorderLayout.CENTER);
		midPanel.add(keyPanel, BorderLayout.SOUTH);

		southPanel.add(resultTextArea, BorderLayout.NORTH);
		southPanel.add(buttonPanel, BorderLayout.SOUTH);
		resultTextArea.setRows(8);
		resultTextArea.setColumns(30);

		content.add(northPanel, BorderLayout.NORTH);
		content.add(midPanel, BorderLayout.CENTER);
		content.add(southPanel, BorderLayout.SOUTH);

		contentPane.add(content);

		pack();
		// Listens to see if the user wishes to change guis
		theBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				String selection = comboBox.getSelectedItem().toString();

				if (selection.equals("None")) {
					makeNoneFrame();
				}
				if (selection.equals("Rotational")) {
					makeRotatedFrame();
				}

			}
		});
		// listens to see if the user switches the rotatin factor
		factorBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				String selection = comboBox.getSelectedItem().toString();
				key = Integer.decode(selection);

			}
		});
		// listens for a press of the encrypt button
		encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				rotateKeyedMessage();
			}
		});
		// listens for a press of the decrypt button
		decryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				decryptKeyedMessage();
			}
		});
	}

	// method that rotates the keyed message and sends the info back to the gui
	private void rotateKeyedMessage() {
		message = theTextArea.getText();
		KeyedRotatedMessage krMessage = new KeyedRotatedMessage(message);
		String keyString = factorBox.getSelectedItem().toString();
		String theSubKey = keyInput.getText();
		key = Integer.decode(keyString);
		krMessage.setRotation(key);
		krMessage.setKey(theSubKey);
		krMessage.encrypt();

		resultTextArea.setText(krMessage.toString());
	}

	// method that decrypts the keyed message and sends the results back to the
	// gui
	private void decryptKeyedMessage() {
		message = resultTextArea.getText();
		KeyedRotatedMessage krMessage = new KeyedRotatedMessage(message);
		String testKey = keyInput.getText();
		if (!testKey.equals("")) {
			krMessage.setKey(keyInput.getText());
			krMessage.decrypt();
			theTextArea.setText(krMessage.toString());
			factorBox.setSelectedItem(krMessage.getRotation());
		} else
			keyInput.setText("Please Enter a Key.");
	}
}
