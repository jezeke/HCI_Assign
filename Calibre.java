package calibre;

import static java.lang.Math.min;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.web.WebEvent;
import javafx.stage.Screen;
import javax.swing.JOptionPane;

/**
*
* @author Jhi Morris
*/
public class Calibre extends Application {

  @Override
  public void start(Stage sPrimary) throws Exception {
    sPrimary.setTitle("Calibre");

    //Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
    //sPrimary.setScene(new Scene(root, 300, 275));
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    webEngine.load(getClass().getResource("www/calibre.html").toString());

    webEngine.setOnAlert(new EventHandler<WebEvent<String>>(){
      @Override
      public void handle(WebEvent<String> arg0) {
        JOptionPane.showMessageDialog(null, arg0.getData(),"Alert", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    webEngine.getLoadWorker().stateProperty().addListener(
    new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != Worker.State.SUCCEEDED) {
          return;
        }

        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("jsInterface", new jsInterface(sPrimary));
      }
    }
    );

    MenuBar mbPrimary = new MenuBar();

    {
      Menu mFile = new Menu("File");
      mFile.getItems().add(new MenuItem("Example"));
      mFile.getItems().add(new SeparatorMenuItem());
      mFile.getItems().add(new MenuItem("Exit"));
      mbPrimary.getMenus().add(mFile);

      Menu mView = new Menu("View");
      mView.getItems().add(new MenuItem("Example"));
      mbPrimary.getMenus().add(mView);

      Menu mSettings = new Menu("Settings");
      mSettings.getItems().add(new MenuItem("Example"));
      mbPrimary.getMenus().add(mSettings);

      Menu mHelp = new Menu("Help");
      MenuItem version = new MenuItem("Version 1.0 of calibre HCI Remake");
      version.setDisable(true);
      mHelp.getItems().add(version);
      mHelp.getItems().add(new SeparatorMenuItem());
      mHelp.getItems().add(new MenuItem("Online Help"));
      mbPrimary.getMenus().add(mHelp);
    }

    VBox vRoot = new VBox(mbPrimary, webView);
    Scene scPrimary;
    {
      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
      double width = screenBounds.getWidth();
      double height = screenBounds.getHeight();

      scPrimary = new Scene(vRoot, min(width/2, height*0.75), height);
    }

    scPrimary.getStylesheets().add(getClass().getResource("calibre.css").toExternalForm());

    sPrimary.setScene(scPrimary);
    sPrimary.show();
  }

  /**
  * @param args the command line arguments
  */
  public static void main(String[] args) {
    launch(args);
  }

  private static class jsInterface {

    Stage sPrimary;

    public jsInterface(Stage sPrimary) {
      this.sPrimary = sPrimary;
    }

    //never managed to get this working whoops
    public void changeTitle(String str) {
      sPrimary.setTitle(str);
    }
  }
}
