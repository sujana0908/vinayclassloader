import util.EncryptionUtility;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncryptedClassLoader extends ClassLoader {

    private final String dir;

    public EncryptedClassLoader(String dir) {
        super(EncryptedClassLoader.class.getClassLoader()); // Use system class loader as parent
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String filePath = dir + "/" + name.replace('.', '/') + ".class";
        try {
            byte[] encryptedData = Files.readAllBytes(Paths.get(filePath));
            byte[] classData = EncryptionUtility.decryptClassData(encryptedData);
            return defineClass(name, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Cannot load class " + name, e);
        }
    }
}
