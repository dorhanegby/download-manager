# download-manager
Submitted by:
Nadav Miran
Dor Hanegby

Description on the submitted file:
1. IdcDm - main class which runs our download manager
2. AbstractDownloadManager - an abstract class, that holds the functionality for our two different types of download managers.
3. DownloadManagerUrl - first implementation of our AbstractDownloadManager, which receives a url as an input, and downloads the file from that url.
4. DownloadManagerList - second implementation of our AbstractDownloadManager, which receives a file path as an input, and downloads the file in parts from the different urls in the given list.
5. Downloader - a single downloading unit. Each downloader receives a bytes range to download from the given file, and sends messages to our queue, with the relevant data.
6. DownloaderContext - a class that holds the data which is necessary for the downloader - the url to download from, the range of bytes to download and the amount of bytes which were already downloaded from that range.
7. BlockingQueue - a singleton class, which allows the downloaders to send messages to the queue. It also allows the Writer to read that messages, and do the necessary processing.
8. Writer - this unit reads messages from the blocking queue, saves the data to the downloaded file, and updates the metadata file for the saved bytes.
9. Message - details of a message which is produced and consumed to and from the queue.
10. MetadataHandler - a singleton class, which saves the metadata update after a successful byte range download. It also allows to deserialize the last state of the download, in case some interrupt occurred and resuming from the point it stopped.
11. PartitioningService - the service allows us split the data of the file we are willing to download into parts. Each part will be downloaded by a different Downloader.
12. ProgressService - this service keeps track on the download. It allows us to know what is the percentage of the file that was already downloaded.
13. Range - holds the start and end byte for a specific downloader.
14. FileSizeService - holds a static method that returns a size of a file located in the given url.
15. FilesUtil - a utility class which extract a file name from a long path, as well as validates if a file exists in a given relative path.
