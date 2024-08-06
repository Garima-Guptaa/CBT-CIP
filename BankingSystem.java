import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(BankAccount otherAccount, double amount) {
        if (withdraw(amount)) {
            otherAccount.deposit(amount);
            return true;
        }
        return false;
    }
}

public class BankingSystem extends JFrame implements ActionListener {
    private Map<String, BankAccount> accounts = new HashMap<>();
    private JTextField accountNumberField, accountHolderField, amountField, transferAccountField;


    public BankingSystem() {
        setTitle("Banking System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton createButton = new JButton("Create Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton transferButton = new JButton("Transfer");
        JButton exitButton = new JButton("Exit");

        add(createButton);
        add(depositButton);
        add(withdrawButton);
        add(checkBalanceButton);
        add(transferButton);
        add(exitButton);

        createButton.addActionListener(this);
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        checkBalanceButton.addActionListener(this);
        transferButton.addActionListener(this);
        exitButton.addActionListener(this);

        setVisible(true);
    }

    private void showOptionsFrame(String title, JPanel panel) {
        JFrame optionsFrame = new JFrame(title);
        optionsFrame.setSize(500, 400);
        optionsFrame.setLayout(new FlowLayout());
        optionsFrame.add(panel);
        optionsFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Create Account":
                JPanel createPanel = new JPanel(new GridLayout(3, 2));
                createPanel.add(new JLabel("Account Number:"));
                accountNumberField = new JTextField(20);
                createPanel.add(accountNumberField);
                createPanel.add(new JLabel("Account Holder:"));
                accountHolderField = new JTextField(20);
                createPanel.add(accountHolderField);
                JButton createAccountButton = new JButton("Create");
                createPanel.add(createAccountButton);
                createAccountButton.addActionListener(event -> {
                    String accountNumber = accountNumberField.getText();
                    String accountHolder = accountHolderField.getText();
                    accounts.put(accountNumber, new BankAccount(accountNumber, accountHolder));
                    JOptionPane.showMessageDialog(this, "Account created successfully.");
                });
                showOptionsFrame("Create Account", createPanel);
                break;

            case "Deposit":
                JPanel depositPanel = new JPanel(new GridLayout(3, 2));
                depositPanel.add(new JLabel("Account Number:"));
                accountNumberField = new JTextField(20);
                depositPanel.add(accountNumberField);
                depositPanel.add(new JLabel("Amount:"));
                amountField = new JTextField(20);
                depositPanel.add(amountField);
                JButton depositButton = new JButton("Deposit");
                depositPanel.add(depositButton);
                depositButton.addActionListener(event -> {
                    String accountNumber = accountNumberField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    BankAccount account = accounts.get(accountNumber);
                    if (account != null) {
                        account.deposit(amount);
                        JOptionPane.showMessageDialog(this, "Amount deposited successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Account not found.");
                    }
                });
                showOptionsFrame("Deposit", depositPanel);
                break;

            case "Withdraw":
                JPanel withdrawPanel = new JPanel(new GridLayout(3, 2));
                withdrawPanel.add(new JLabel("Account Number:"));
                accountNumberField = new JTextField(20);
                withdrawPanel.add(accountNumberField);
                withdrawPanel.add(new JLabel("Amount:"));
                amountField = new JTextField(20);
                withdrawPanel.add(amountField);
                JButton withdrawButton = new JButton("Withdraw");
                withdrawPanel.add(withdrawButton);
                withdrawButton.addActionListener(event -> {
                    String accountNumber = accountNumberField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    BankAccount account = accounts.get(accountNumber);
                    if (account != null && account.withdraw(amount)) {
                        JOptionPane.showMessageDialog(this, "Amount withdrawn successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient balance or account not found.");
                    }
                });
                showOptionsFrame("Withdraw", withdrawPanel);
                break;

            case "Check Balance":
                JPanel balancePanel = new JPanel(new GridLayout(2, 2));
                balancePanel.add(new JLabel("Account Number:"));
                accountNumberField = new JTextField(20);
                balancePanel.add(accountNumberField);
                JButton checkBalanceButton = new JButton("Check Balance");
                balancePanel.add(checkBalanceButton);
                checkBalanceButton.addActionListener(event -> {
                    String accountNumber = accountNumberField.getText();
                    BankAccount account = accounts.get(accountNumber);
                    if (account != null) {
                        JOptionPane.showMessageDialog(this, "Current Balance: " + account.getBalance());
                    } else {
                        JOptionPane.showMessageDialog(this, "Account not found.");
                    }
                });
                showOptionsFrame("Check Balance", balancePanel);
                break;

            case "Transfer":
                JPanel transferPanel = new JPanel(new GridLayout(4, 2));
                transferPanel.add(new JLabel("From Account:"));
                accountNumberField = new JTextField(20);
                transferPanel.add(accountNumberField);
                transferPanel.add(new JLabel("To Account:"));
                transferAccountField = new JTextField(20);
                transferPanel.add(transferAccountField);
                transferPanel.add(new JLabel("Amount:"));
                amountField = new JTextField(20);
                transferPanel.add(amountField);
                JButton transferButton = new JButton("Transfer");
                transferPanel.add(transferButton);
                transferButton.addActionListener(event -> {
                    String fromAccountNumber = accountNumberField.getText();
                    String toAccountNumber = transferAccountField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    BankAccount fromAccount = accounts.get(fromAccountNumber);
                    BankAccount toAccount = accounts.get(toAccountNumber);
                    if (fromAccount != null && toAccount != null && fromAccount.transfer(toAccount, amount)) {
                        JOptionPane.showMessageDialog(this, "Transfer successful.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Transfer failed. Check account details and balance.");
                    }
                });
                showOptionsFrame("Transfer", transferPanel);
                break;

            case "Exit":
                System.exit(0);
                break;
        }
    }

    public static void main(String[] args) {
        new BankingSystem();
    }
}
