class IdcDm {

    public static void main(String[] args) {
        String path = args[0];
        int numOfThreads = 1;
        if (args.length >= 2){
            numOfThreads = Integer.parseInt(args[1]);
        }
        AbstractDownloadManager dm = DownloadManagerFactory(path, numOfThreads);
        try {
            dm.download();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Download succeeded");
    }

    private static AbstractDownloadManager DownloadManagerFactory(String urlOrPath, int numberOfThreads){
        if (urlOrPath.startsWith("http")){
            return new DownloadManagerUrl(urlOrPath, numberOfThreads);
        }

        return new DownloadManagerList(urlOrPath, numberOfThreads);
    }
}
