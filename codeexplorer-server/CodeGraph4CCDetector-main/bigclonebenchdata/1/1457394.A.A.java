public class A{
    private File download(String filename, URL url) {
        int size = -1;
        int received = 0;
        try {
            fireDownloadStarted(filename);
            File file = createFile(filename);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            System.out.println("������Դ��" + filename + ", url=" + url);
            InputStream bis = url.openStream();
            byte[] buf = new byte[1024];
            int count = 0;
            long lastUpdate = 0;
            size = bis.available();
            while ((count = bis.read(buf)) != -1) {
                bos.write(buf, 0, count);
                received += count;
                long now = System.currentTimeMillis();
                if (now - lastUpdate > 500) {
                    fireDownloadUpdate(filename, size, received);
                    lastUpdate = now;
                }
            }
            bos.close();
            System.out.println("��Դ������ϣ�" + filename);
            fireDownloadCompleted(filename);
            return file;
        } catch (IOException e) {
            System.out.println("������Դʧ�ܣ�" + filename + ", error=" + e.getMessage());
            fireDownloadInterrupted(filename);
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }
        return null;
    }
}