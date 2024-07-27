import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class ManageFiles {
    private int gridSize;
    private final String highScorePath = "highscore";

    public ManageFiles(int size) {
        this.gridSize = size;
    }

    public ManageFiles(){}

    ArrayList<String> getImages(String imageDirectory) {
        ArrayList<String> imgPath = new ArrayList<>();

        File file = new File(imageDirectory);
        File[] imagePath = file.listFiles();

        if (imagePath == null) {
            System.out.println("Empty Folder");
        }

        for (File f : imagePath) {
            if (imgPath.size() < (gridSize / 2)) {
                String path = imageDirectory + "\\" + f.getName();
                if (path.endsWith(".jpeg") || path.endsWith(".jpg") || path.endsWith(".png"))
                    imgPath.add(path);
            } else {
                break;
            }
        }

        if (imgPath.size() < (gridSize / 2)) {
            System.out.println("No Enough Images Found!");
        }

        imgPath.addAll(imgPath); // Duplicates the existing items
        Collections.shuffle(imgPath); // Shuffles the ArrayList
        return imgPath;
    }

    ArrayList<String> getImages(ArrayList<String> img) {
        ArrayList<String> imgPath = new ArrayList<>();

        if (img == null) {
            System.out.println("Empty Folder");
        }

        for (String i : img) {
            if (imgPath.size() < (gridSize / 2)) {
                String lower = i.toLowerCase();
                if (lower.endsWith(".jpeg") || lower.endsWith(".jpg") || lower.endsWith(".png"))
                    imgPath.add(i);
            } else {
                break;
            }
        }

        if (imgPath.size() < (gridSize / 2)) {
            System.out.println("No Enough Images Found!" + "Only " + imgPath.size() + " Found. Required " + (gridSize / 2));
        }

        imgPath.addAll(imgPath); // Duplicates the existing items
        Collections.shuffle(imgPath); // Shuffles the ArrayList
        return imgPath;
    }

    // Writes a new Highscore.
    public void highScoreWriter(int highscore, int level) {
        int previousHighscore = getHighScore(level);
        if (highscore > previousHighscore) {
            try {
                String path = highScorePath + "/hs" + Integer.toString(level);
                BufferedOutputStream binaryWriter = new BufferedOutputStream(new FileOutputStream(path));
                binaryWriter.write(highscore);
                binaryWriter.close();
            } catch (IOException e) {
                System.out.println("Highscore was not saved properly.");
            }
        }
    }

    // Gets the highscore from the above path.
    public int getHighScore(int level) {
        int highScore = 0;
        String path = highScorePath + "/hs" + Integer.toString(level);
        try {
            BufferedInputStream binaryReader = new BufferedInputStream(new FileInputStream(path));
            highScore = binaryReader.read();
            binaryReader.close();
        } catch (IOException e) {
            System.out.println("Highscore was not read successfully.");
        }
        return highScore;
    }
}
