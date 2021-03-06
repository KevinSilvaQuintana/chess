package net;

import gui.Driver;
import gui.NetLoadingPanel;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utility.GuiUtility;

public final class NetworkPlayManager
{
	private NetworkPlayManager()
	{
	}

	public static NetworkPlayManager getInstance()
	{
		if (sInstance == null)
			sInstance = new NetworkPlayManager();

		return sInstance;
	}

	public boolean networkPlayIsAvailable()
	{
//		try
//		{
//			return InetAddress.getLocalHost().getHostName().contains("cslab");
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}

		// TODO: This is only false because we don't have network play right now
		return false;
	}

	public ActionListener createNetworkPlayActionListener()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				final JFrame popupFrame = new JFrame("New Game");
				popupFrame.setLayout(new FlowLayout());
				popupFrame.setSize(350, 150);
				popupFrame.setResizable(false);
				popupFrame.setLocationRelativeTo(Driver.getInstance());

				JPanel optionsPanel = new JPanel();
				final JLabel hostOrConnectLabel = new JLabel("Would you like to host a game or connect to one?");
				final JButton clientButton = new JButton("Connect");
				clientButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event)
					{
						final JFrame poppedFrame = new JFrame("New Game");
						poppedFrame.setLayout(new GridBagLayout());
						poppedFrame.setSize(370, 150);
						poppedFrame.setResizable(false);
						poppedFrame.setLocationRelativeTo(Driver.getInstance());
						GridBagConstraints netGameConstraints = new GridBagConstraints();

						final JLabel connectToLabel = new JLabel("Which computer would you like to connect to?");
						final JTextField connectToField = new JTextField("", 2);

						final JButton nextButton = new JButton("Next");
						nextButton.addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent event)
							{
								if (connectToField.getText().equals(""))
								{
									JOptionPane.showMessageDialog(Driver.getInstance(), "Please enter a number", "Number Needed",
											JOptionPane.PLAIN_MESSAGE);
									return;
								}
								else if (connectToField.getText().length() < 2)
								{
									try
									{
										int hostNumber = Integer.parseInt(connectToField.getText());
										if (hostNumber > 25 || hostNumber < 1)
											throw new Exception();
										mHostName = "cslab0" + hostNumber;
									}
									catch (Exception e)
									{
										JOptionPane.showMessageDialog(Driver.getInstance(), "Please enter a number between 1-25 in the box",
												"Number Needed", JOptionPane.PLAIN_MESSAGE);
										return;
									}
								}
								else
								{
									try
									{
										int hostNumber = Integer.parseInt(connectToField.getText());
										if (hostNumber > 25 || hostNumber < 1)
											throw new Exception();
										mHostName = "cslab" + hostNumber;
									}
									catch (Exception e)
									{
										JOptionPane.showMessageDialog(Driver.getInstance(), "Please enter a number between 1-25 in the box",
												"Number Needed", JOptionPane.PLAIN_MESSAGE);
										return;
									}
								}
//								NewGameMenu.mIsCancelled = false;
								try
								{
									Thread clientThread = new Thread(new Runnable()
									{
										@Override
										public void run()
										{
											try
											{
												new NetworkClient().join(mHostName);
											}
											catch (Exception e)
											{
												e.printStackTrace();
											}
										}
									});
									clientThread.start();
									Driver.getInstance().setPanel(new NetLoadingPanel());
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}

								poppedFrame.dispose();
								popupFrame.dispose();
							}
						});

						final JButton cancelButton = new JButton("Cancel");
						GuiUtility.setupCancelButton(cancelButton, popupFrame);

						JPanel topLevelPanel = new JPanel();
						topLevelPanel.setLayout(new GridBagLayout());

						netGameConstraints.gridx = 0;
						netGameConstraints.gridy = 0;
						netGameConstraints.gridwidth = 2;
						netGameConstraints.insets = new Insets(3, 3, 3, 3);
						poppedFrame.add(connectToLabel, netGameConstraints);
						netGameConstraints.gridx = 0;
						netGameConstraints.gridy = 1;
						netGameConstraints.gridwidth = 1;
						topLevelPanel.add(new JLabel("cslab: "), netGameConstraints);
						netGameConstraints.gridx = 1;
						netGameConstraints.gridy = 1;
						netGameConstraints.gridwidth = 1;
						topLevelPanel.add(connectToField, netGameConstraints);
						netGameConstraints.gridx = 0;
						netGameConstraints.gridy = 2;
						netGameConstraints.gridwidth = 1;
						topLevelPanel.add(nextButton, netGameConstraints);
						netGameConstraints.gridx = 1;
						netGameConstraints.gridy = 2;
						netGameConstraints.gridwidth = 1;
						topLevelPanel.add(cancelButton, netGameConstraints);

						netGameConstraints.gridx = 0;
						netGameConstraints.gridy = 1;
						netGameConstraints.gridwidth = 2;
						poppedFrame.add(topLevelPanel, netGameConstraints);
						poppedFrame.setVisible(true);
					}
				});

				final JButton hostButton = new JButton("Host");
				hostButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event)
					{
						// TODO: Need to do something here...
						// here's what we used to do in NewGameMenu.createNewGamePopup
//						if (isNetworkPlay)
//						{
//							List<String> filteredList = Lists.newArrayList();
//							for (String variant : variantTypes)
//							{
//								Game game = Builder.newGame(variant);
//								if (game.getWhiteRules().rulesAreNetworkable() && game.getBlackRules().rulesAreNetworkable())
//									filteredList.add(variant);
//							}
//							variantTypes = new String[filteredList.size()];
//							int i = 0;
//							for (String variantName : filteredList)
//								variantTypes[i++] = variantName;
//						}

//						Game gameToPlay = Builder.newGame((String) dropdown.getSelectedItem());
//						gameToPlay.setTimers(whiteTimer, blackTimer);
//						final PlayNetGame game;
//						// TODO: this if statement can't be right...
//						if (mHostName.equals(event.toString()))
//						{
//							try
//							{
//								game = new PlayNetGame(gameToPlay, false, true);
//							}
//							catch (Exception e)
//							{
//								return;
//							}
//						}
//						else
//						{
//							try
//							{
//								game = new PlayNetGame(gameToPlay, false, false);
//							}
//							catch (Exception e)
//							{
//								return;
//							}
//						}
//						try
//						{
//							NewGameMenu.mIsCancelled = false;
//							Thread host = new Thread(new Runnable()
//							{
//								@Override
//								public void run()
//								{
//									try
//									{
//										new NetworkServer().host(game);
//									}
//									catch (Exception e)
//									{
//										e.printStackTrace();
//									}
//								}
//							});
//							Driver.getInstance().setPanel(new NetLoading());
//							host.start();
//						}
//						catch (Exception e)
//						{
//							System.out.println("Host");
//							e.printStackTrace();
//						}

						popupFrame.dispose();
					}
				});
				optionsPanel.add(hostOrConnectLabel);
				popupFrame.add(optionsPanel);
				popupFrame.add(hostButton);
				popupFrame.add(clientButton);

				popupFrame.setVisible(true);
			}
		};
	}

	private static NetworkPlayManager sInstance;

	private String mHostName = "";
}
