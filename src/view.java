import dataBase.DataBaseConnection;
import dataBase.QueryManager;
import dataModel.Category;
import dataModel.Product;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class view extends JFrame {
    private JPanel JPanel1;
    private JTable tabSelectList;
    private JTabbedPane tabbedPane1;
    private JPanel Products;
    private JTree categoryList;
    private JTextField productPrice;
    private JTextField productName;
    private JTextArea productDesc;
    private JButton addNewProductStart;
    private JTextField productQty;
    private JComboBox productCategory;
    private JCheckBox productOnSale;
    private JTextField productSaleFrom;
    private JTextField productSaleTo;
    private JButton productEdit;
    private JButton productSave;
    private JButton productDelete;
    private JTextField productSalePrice;
    private JTextField categoryName;
    private JComboBox categoryParent;
    private JButton categoryDelete;
    private JButton categoryAddNew;
    private JButton categoryEdit;
    private JButton categorySave;
    private JTextField categoryDesc;
//    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Sklep");
//    DefaultTreeModel treeModel = new DefaultTreeModel(root);

    static DataBaseConnection manager = new DataBaseConnection();


//    public static final String DRIVER = "org.sqlite.JDBC";
//    public static final String DB_URL = "jdbc:sqlite:custom.db";
//    private Statement stat;


    public static void main(String[] args) {

        view obj = new view();
        obj.setVisible(true);
    }

    public view() {
        super("tekst");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1240, 750);

        boxSettings();
        //SampleData.AddSampleProducts();
        createCategoryTree();
        CategoryPage();
        createProductViewTable();
        saleModule();

    }

    private void refresh() {
        List<Product> productsLite = QueryManager.selectProductsLite();
        List<Product> products = QueryManager.selectProducts();

        assert productsLite != null;
        Object[][] data = new Object[productsLite.size()][2];
        for (int i = 0; i < productsLite.size(); i++) {
            data[i][0] = productsLite.get(i).getId();
            data[i][1] = productsLite.get(i).getName();
        }
        tabSelectList.setModel(new DefaultTableModel(
                data,
                new String[]{"ID", "Nazwa"}
        ));
        TableColumnModel columnModel = tabSelectList.getColumnModel();
        columnModel.getColumn(0).setMinWidth(30);
        columnModel.getColumn(0).setMaxWidth(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.LEFT);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        columnModel.getColumn(1).setMinWidth(60);
    }

    private void createProductViewTable() {
        List<Product> productsLite = QueryManager.selectProductsLite();
        List<Product> products = QueryManager.selectProducts();

        assert productsLite != null;
        Object[][] data = new Object[productsLite.size()][2];
        for (int i = 0; i < productsLite.size(); i++) {
            data[i][0] = productsLite.get(i).getId();
            data[i][1] = productsLite.get(i).getName();
        }

        tabSelectList.setModel(new DefaultTableModel(
                data,
                new String[]{"ID", "Nazwa"}
        ));
        tableConfig(products);
    }

    private void CategoryPage() {
        categoryAddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryName.setEditable(true);
                categoryDesc.setEditable(true);
                categorySave.setEnabled(true);
            }
        });

        categoryDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryList.getLastSelectedPathComponent();
                String selectedCategoryName = selectedNode.toString();

                ArrayList<Category> categories = QueryManager.selectCategories();
                int selectedCategoryId = -1;

                for (Category category : categories) {
                    if (category.getName().equals(selectedCategoryName)) {
                        selectedCategoryId = category.getId();
                        break;
                    }
                }

                QueryManager.deleteCategory(selectedCategoryId);
                refreshCategoryTree();
            }
        });

        categoryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryList.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    categoryEdit.setEnabled(true);
                    categoryName.setText(selectedNode.toString());
                    categoryDesc.setText(selectedNode.toString());
                } else {
                    refreshCategoryTree();
                }
                categoryEdit.setEnabled(true);
                categoryName.setText(selectedNode.toString());
                categoryDesc.setText(selectedNode.toString());
            }
        });

        categoryEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryName.setEditable(true);
                categoryDesc.setEditable(true);
                categorySave.setEnabled(true);
            }
        });
        categorySave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryList.getLastSelectedPathComponent();
                String selectedCategoryName = selectedNode.toString();

                ArrayList<Category> categories = QueryManager.selectCategories();
                Category currCategory = null;
                int selectedCategoryId = -1;

                for (Category category : categories) {
                    if (category.getName().equals(selectedCategoryName)) {
                        selectedCategoryId = category.getId();
                        currCategory = category;
                    }
                }
                String name = categoryName.getText();
                String desc = categoryDesc.getText();

                if (selectedCategoryId == -1) {
                    Category newCategory = new Category(name, desc);
                    QueryManager.insertCategory(newCategory);
                } else {
                    currCategory.setName(name);
                    currCategory.setDesc(desc);
                    QueryManager.updateCategory(currCategory);
                }
                categoryName.setEditable(false);
                categoryDesc.setEditable(false);
                categorySave.setEnabled(false);
                categoryName.setText("");
                categoryDesc.setText("");
                refreshCategoryTree();
            }
        });

    }

    private void refreshCategoryTree() {
        ArrayList<Category> categories = QueryManager.selectCategories();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Kategorie");

        for (Category category : categories) {
            DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(category.getName());

            root.add(categoryNode);
        }
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        categoryList.setModel(treeModel);

    }

    private void createCategoryTree() {

        ArrayList<Category> categories = QueryManager.selectCategories();
        assert categories != null;

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Kategorie");

        for (Category category : categories) {
            DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(category.getName());

            root.add(categoryNode);
        }
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        categoryList.setModel(treeModel);
    }


    public void tableConfig(List<Product> products) {
        TableColumnModel columnModel = tabSelectList.getColumnModel();
        columnModel.getColumn(0).setMinWidth(30);
        columnModel.getColumn(0).setMaxWidth(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.LEFT);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        columnModel.getColumn(1).setMinWidth(60);

        tabSelectList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {

                    int selectedRow = tabSelectList.getSelectedRow();
                    if (selectedRow >= 0) {
                        int selectedProductId = (int) tabSelectList.getValueAt(selectedRow, 0);
                        Product selectedProduct = Product.findProduct(selectedProductId);

                        assert selectedProduct != null;
                        productName.setText(selectedProduct.getName());
                        productName.setEditable(false);
                        productDesc.setText(selectedProduct.getDesc());
                        productDesc.setEditable(false);
                        productPrice.setEditable(false);
                        productPrice.setText(String.valueOf(selectedProduct.getPrice()));
                        productOnSale.setEnabled(false);
                        productSalePrice.setEnabled(false);
                        productSaleFrom.setEnabled(false);
                        productSaleTo.setEnabled(false);
                        if (selectedProduct.getSalePrice() != 0) {
                            productOnSale.setSelected(true);
                            productSalePrice.setText(String.valueOf(selectedProduct.getSalePrice()));
                            productSaleFrom.setText(String.valueOf(selectedProduct.getSaleFrom()));
                            productSaleTo.setText(String.valueOf(selectedProduct.getSaleTo()));
                        } else {
                            productOnSale.setSelected(false);
                            productSalePrice.setText("");
                            productSaleFrom.setText("");
                            productSaleTo.setText("");
                        }
                        productQty.setEditable(false);
                        productQty.setText(String.valueOf(selectedProduct.getQty()));
                        productEdit.setEnabled(true);
                        productSave.setEnabled(false);

                        int categoryId = selectedProduct.getCategoryId();
                        if (categoryId == 0) {
                            productCategory.setSelectedIndex(-1);
                        } else {
                            Category category = QueryManager.selectCategoryById(categoryId);
                            productCategory.setSelectedItem(category.getName());
                        }
                        productCategory.setEnabled(false);

                    }
                }
            }
        });
        productCategory.setEnabled(false);
        ArrayList<Category> categories = QueryManager.selectCategories();
        assert categories != null;
        for (Category category : categories) {
            productCategory.addItem(category.getName());
        }

        productEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productName.setEditable(true);
                productDesc.setEditable(true);
                productPrice.setEditable(true);
                productQty.setEditable(true);
                productOnSale.setEnabled(true);
                productCategory.setEnabled(true);
                productSave.setEnabled(true);
                productSalePrice.setEnabled(true);
            }
        });

        productDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabSelectList.getSelectedRow();
                int selectedProductId = (int) tabSelectList.getValueAt(selectedRow, 0);

                QueryManager.deleteProduct(selectedProductId);

                refresh();

                productName.setText("");
                productPrice.setText("");
                productQty.setText("");
                productDesc.setText("");
                productSaleFrom.setText("");
                productSaleTo.setText("");
                productSave.setEnabled(false);
                productName.setEditable(false);
                productDesc.setEditable(false);
                productPrice.setEditable(false);
                productQty.setEditable(false);
                productOnSale.setEnabled(false);
                productCategory.setEnabled(false);
                productSalePrice.setEnabled(false);
            }
        });

        productSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (tabSelectList.getSelectedRow() >= 0) {
                    int selectedRow = tabSelectList.getSelectedRow();
                    int selectedProductId = (int) tabSelectList.getValueAt(selectedRow, 0);

                    Product updatedProduct = QueryManager.selectProductById(selectedProductId);

                    String name = productName.getText();
                    String desc = productDesc.getText();
                    double price = Double.parseDouble(productPrice.getText());
                    int qty = Integer.parseInt(productQty.getText());
                    double salePrice = Double.parseDouble(productSalePrice.getText());
                    String saleFrom = productSaleFrom.getText();
                    String saleTo = productSaleTo.getText();

                    int categoryId = 0;
                    String categoryName = (String) productCategory.getSelectedItem();

                    ArrayList<Category> categories = QueryManager.selectCategories();

                    assert categories != null;
                    for (Category category : categories) {
                        if (category.getName().equals(categoryName)) {
                            categoryId = category.getId();
                            break;
                        }
                    }

                    updatedProduct.setName(name);
                    updatedProduct.setDesc(desc);
                    updatedProduct.setPrice(price);
                    updatedProduct.setQty(qty);
                    updatedProduct.setCategoryId(categoryId);
                    if (productOnSale.isSelected()) {
                        updatedProduct.setSalePrice(salePrice);
                        updatedProduct.setSaleFrom(saleFrom);
                        updatedProduct.setSaleTo(saleTo);
                    } else {
                        updatedProduct.setSalePrice(0);
                        updatedProduct.setSaleFrom("");
                        updatedProduct.setSaleTo("");
                    }

                    QueryManager.updateProduct(updatedProduct);

                    //updatedProduct.setQty(qty);

                    productSave.setEnabled(false);
                    refresh();

                } else {
                    Product newProduct;

                    String name = productName.getText();
                    String desc = productDesc.getText();
                    double price = Double.parseDouble(productPrice.getText());
                    int qty = Integer.parseInt(productQty.getText());
                    int categoryId = 0;
                    String categoryName = (String) productCategory.getSelectedItem();

                    ArrayList<Category> categories = QueryManager.selectCategories();

                    for (Category category : categories) {
                        if (category.getName().equals(categoryName)) {
                            categoryId = category.getId();
                            break;
                        }
                    }

                    if (productOnSale.isSelected()) {
                        double salePrice = Double.parseDouble(productSalePrice.getText());
                        String saleFrom = productSaleFrom.getText();
                        String saleTo = productSaleTo.getText();

                        newProduct = new Product(name, desc, price, salePrice, saleFrom, saleTo, qty, categoryId);
                    } else {
                        newProduct = new Product(name, desc, price, qty, categoryId);
                    }


                    QueryManager.insertProducts(newProduct);

                    productSave.setEnabled(false);
                    refresh();

                    int lastRow = tabSelectList.getRowCount() - 1;
                    tabSelectList.setRowSelectionInterval(lastRow, lastRow);
                }
            }
        });

        addNewProductStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabSelectList.clearSelection();
                productName.setText("");
                productName.setEditable(true);
                productPrice.setText("");
                productPrice.setEditable(true);
                productQty.setText("");
                productQty.setEditable(true);
                productDesc.setText("");
                productDesc.setEditable(true);
                productOnSale.setEnabled(true);
                productOnSale.setSelected(false);
                productSaleFrom.setText("");
                productSaleTo.setText("");
                productCategory.setSelectedIndex(-1);
                productCategory.setEnabled(true);
                productEdit.setEnabled(false);
                productSave.setEnabled(true);
            }
        });
    }

    public void boxSettings() {
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        productDesc.setBorder(border);
        productOnSale.setBorder(border);
        productName.setBorder(border);
        productDesc.setBorder(border);
        productPrice.setBorder(border);
        productQty.setBorder(border);
        productSaleFrom.setBorder(border);
        productSaleTo.setBorder(border);
        productSalePrice.setBorder(border);
        productCategory.setBorder(border);
    }

    public void saleModule() {
        productOnSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (productOnSale.isSelected()) {
                    productSaleFrom.setEditable(true);
                    productSaleFrom.setEnabled(true);
                    productSaleTo.setEditable(true);
                    productSaleTo.setEnabled(true);
                    productSalePrice.setEnabled(true);
                    productSalePrice.setEditable(true);
                } else {
                    productSaleFrom.setEditable(false);
                    productSaleFrom.setEnabled(false);
                    productSaleTo.setEditable(false);
                    productSaleTo.setEnabled(false);
                    productSalePrice.setEnabled(false);
                    productSalePrice.setEditable(false);
                }
            }
        });
    }
}