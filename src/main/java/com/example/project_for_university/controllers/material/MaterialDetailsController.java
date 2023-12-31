package com.example.project_for_university.controllers.material;

import com.example.project_for_university.controllers.material.models.TableType;
import com.example.project_for_university.dto.AllValues;
import com.example.project_for_university.dto.forBackend.entity.ConditionEntity;
import com.example.project_for_university.dto.forBackend.entity.LayerEntity;
import com.example.project_for_university.dto.forBackend.entity.types.*;
import com.example.project_for_university.enums.Component;
import com.example.project_for_university.providers.DataProvider;
import com.example.project_for_university.utils.ComponentUtil;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MaterialDetailsController implements Initializable, DataProvider {
    private AllValues allValues;
    private int curIndex = 0;

    private ArrayList<Image> images = new ArrayList<>();
    private PartialMaterialEntity partialMaterialEntity;

    @FXML
    private ImageView image_view;

    @FXML
    private Label pagination_lbl;

    @FXML
    private HBox back_btn;

    @FXML
    private HBox download_report_btn;

    @FXML
    private Hyperlink email_lbl;

    @FXML
    private Text engineer_name_text;

    @FXML
    private HBox image_back_btn;

    @FXML
    private HBox image_next_btn;

    @FXML
    private Text material_desc_text;

    @FXML
    private Label material_name_lbl;

    //---layer-table---
    @FXML
    private TableView<TableType> layers_table;

    @FXML
    private TableColumn<TableType, String> index_layer_column;

    @FXML
    private TableColumn<TableType, String> type_layer_column;

    //---condition-table---
    @FXML
    private TableView<TableType> condition_params_table;

    @FXML
    private TableColumn<TableType, String> name_condition_param_column;

    @FXML
    private TableColumn<TableType, String> value_condition_param_column;

    //---load-type-table---
    @FXML
    private TableView<TableType> load_type_table;

    @FXML
    private TableColumn<TableType, String> type_name_column;

    @FXML
    private TableColumn<TableType, String> values_type_column;

    //---material-details-table---
    @FXML
    private TableView<TableType> material_details_table;

    @FXML
    private TableColumn<TableType, String> name_material_details_column;

    @FXML
    private TableColumn<TableType, String> value_material_details_column;

    @Override
    public void setData(AllValues allValues) {
        this.allValues = allValues;
    }

    public void setPartialMaterialEntity(PartialMaterialEntity partialMaterialEntity) {
        this.partialMaterialEntity = partialMaterialEntity;

        Arrays.stream(partialMaterialEntity.getImages()).forEach(image -> images.add(new Image(image)));
        if (images.size() == 0) {
            image_view.setImage(new Image(getClass().getResourceAsStream("/com/example/project_for_university/img/no-image.png")));
            pagination_lbl.setVisible(false);
            pagination_lbl.setManaged(false);
        } else {
            image_view.setImage(images.get(0));
            pagination_lbl.setText("1/" + images.size());
        }

        material_name_lbl.setText(partialMaterialEntity.getName());
        material_desc_text.setText(partialMaterialEntity.getDescription());
        engineer_name_text.setText(partialMaterialEntity.getUser().getFio());
        email_lbl.setText(partialMaterialEntity.getUser().getEmail());

        ObservableList<TableType> layerEntities = FXCollections.observableArrayList();
        for (LayerEntity layer : partialMaterialEntity.getLayers()) {
            layerEntities.add(new TableType(String.valueOf(layer.getIndexNum()), layer.getLayerType().getName()));
        }
        layers_table.setItems(layerEntities);

        material_details_table.setItems(FXCollections.observableArrayList(
                new TableType("Толщина", String.valueOf(partialMaterialEntity.getDepth()) + " мм"),
                new TableType("Способ производства", partialMaterialEntity.getManufacturer())
        ));

        ConditionEntity condition = partialMaterialEntity.getCondition();
        ObservableList<TableType> loadTypeItems = FXCollections.observableArrayList(
                !Objects.isNull(condition.getBendingType()) ? new TableType("Изгиб", condition.getBendingType().getName()) : null,
                !Objects.isNull(condition.getAbrasionType()) ? new TableType("Истирание", condition.getAbrasionType().getName()) : null,
                condition.getStretchingCompression() != 0 ? new TableType("Растяжение-сжатие", condition.getStretchingCompression() + "%") : null,
                condition.getTorsionAngle() != 0 ? new TableType("Кручение", condition.getTorsionAngle() + "°") : null,
                !Objects.isNull(condition.getWashing()) ? new TableType("Стирка", partialMaterialEntity.getName() + ", "
                        + partialMaterialEntity.getCondition().getWashing().getCyclesCnt() + " циклов, "
                        + partialMaterialEntity.getCondition().getWashing().getTemperature() + "°С, "
                        + partialMaterialEntity.getCondition().getWashing().getDuration() + " мин, "
                        + (partialMaterialEntity.getCondition().getWashing().isPress() ? "с отжимом" : "без отжима")
                ) : null
        );
        load_type_table.setItems(loadTypeItems.stream().filter(Objects::nonNull).collect(Collectors.toCollection(FXCollections::observableArrayList)));

        condition_params_table.setItems(FXCollections.observableArrayList(
                new TableType("Знак при минимальной температуре воздуха", partialMaterialEntity.getCondition().isPositive() ? "Плюс" : "Минус"),
                new TableType("Минимальная температура воздуха", partialMaterialEntity.getCondition().getMinAirTemp() + "°С"),
                new TableType("Максимальная температура воздуха", partialMaterialEntity.getCondition().getMaxAirTemp() + "°С"),
                new TableType("Минимальная влажность воздуха", partialMaterialEntity.getCondition().getMinAirHumidity() + "%"),
                new TableType("Максимальная влажность воздуха", partialMaterialEntity.getCondition().getMaxAirHumidity() + "%"),
                new TableType("Средняя скорость движения воздуха", partialMaterialEntity.getCondition().getAvgAirSpeed() + " м/с"),
                new TableType("Время неприрывного пребывания в заданных условиях", partialMaterialEntity.getCondition().getResidenceTime() + " ч")
        ));
    }

    @FXML
    @SneakyThrows()
    void back_btn_clicked(MouseEvent event) {
        ComponentUtil.mount(Component.FILTER, allValues.getContentPanes().getLoggedInStackPane(), allValues);
    }

    @FXML
    void download_report_btn_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузка отчета");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName(partialMaterialEntity.getName() + ".xlsx");
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            System.out.println("Selected file: " + file.getAbsolutePath());
        } else {
            System.out.println("Operation canceled");
        }
        stage.show();
    }

    @FXML
    void image_back_btn_clicked(MouseEvent event) {
        if (images.size() != 0 && curIndex - 1 >= 0) {
            image_view.setImage(images.get(curIndex - 1));
            curIndex -= 1;
            pagination_lbl.setText((curIndex + 1) + "/" + images.size());
        }
    }

    @FXML
    void image_next_btn_clicked(MouseEvent event) {
        if (images.size() != 0 && curIndex + 1 < images.size()) {
            image_view.setImage(images.get(curIndex + 1));
            curIndex += 1;
            pagination_lbl.setText((curIndex + 1) + "/" + images.size());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        index_layer_column.setCellValueFactory(new PropertyValueFactory<>("firstCol"));
        type_layer_column.setCellValueFactory(new PropertyValueFactory<>("secondCol"));

        name_material_details_column.setCellValueFactory(new PropertyValueFactory<>("firstCol"));
        value_material_details_column.setCellValueFactory(new PropertyValueFactory<>("secondCol"));

        type_name_column.setCellValueFactory(new PropertyValueFactory<>("firstCol"));
        values_type_column.setCellValueFactory(new PropertyValueFactory<>("secondCol"));

        name_condition_param_column.setCellValueFactory(new PropertyValueFactory<>("firstCol"));
        value_condition_param_column.setCellValueFactory(new PropertyValueFactory<>("secondCol"));
    }
}
