import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.thelensky.vr.VRImage;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class VRImageTest {
    @TempDir
    static Path path = Path.of(System.getProperty("user.dir")).resolve("src/test/resources/test.jpg");

    @Test
    @DisplayName("image loaded")
    void loadedImage() {
        var widthOfImage = 571;
        var heightOfImage = 800;

        var sut = new VRImage(path.toString());

        assertNotNull(sut.getImg());
        assertEquals(widthOfImage, sut.getImg().getWidth());
        assertEquals(heightOfImage, sut.getImg().getHeight());
    }

    @Test
    @DisplayName("creates a new image from the previous one by slicing it by width")
    void sliceImg() {
        var startSlice = 0;
        var endSlice = 20;
        var sut = factorySUT();

        var sliced = sut.slice(startSlice, endSlice);

        assertEquals(endSlice, sliced.getWidth());
        assertEquals(sut.getImg().getHeight(), sliced.getHeight());
    }

    @Test
    @DisplayName("merge two images into one")
    void mergeTwoImg() {
        var startSlice = 0;
        var endSlice = 20;
        var sut = factorySUT();
        var leftImg = sut.slice(startSlice, endSlice);
        var rightImg = sut.slice(startSlice, endSlice);

        var merged = VRImage.mergeImages(leftImg, rightImg);

        assertEquals(endSlice * 2, merged.getWidth());
        assertEquals(sut.getImg().getHeight(), merged.getHeight());
        assertEquals(sut.getImg().getRGB(1, 1), merged.getRGB(1, 1));
        assertNotEquals(sut.getImg().getRGB(2, 1), merged.getRGB(1, 1));
        assertEquals(sut.getImg().getRGB(1, 1), merged.getRGB(1 + endSlice, 1));
        assertEquals(merged.getRGB(1, 1), merged.getRGB(1 + endSlice, 1));
    }

    @Test
    @DisplayName("create image for vr glasses")
    void imageForVRGlasses() {
        File file = new File (VRImage.makeImg(path.toString()));

        assertTrue(file.exists());

        //file.delete();
    }

    // System under test factory
    VRImage factorySUT() {
        return new VRImage(path.toString());
    }
}