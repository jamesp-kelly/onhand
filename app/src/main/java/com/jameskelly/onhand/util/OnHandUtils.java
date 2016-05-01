package com.jameskelly.onhand.util;

import android.os.Environment;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class OnHandUtils {

  public static String formattedDate(long millis) {

    DateTimeFormatter archiveDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
    DateTimeFormatter archiveTimeFormat = DateTimeFormat.forPattern("HH:mm");

    DateTime dateTime = new DateTime(millis);
    final LocalDate localDate = dateTime.toLocalDate();

    String dateText;

    if (localDate.equals(new LocalDate())) {
      dateText = "Today";
    } else if (localDate.equals(new LocalDate().minusDays(1))) {
      dateText = "Yesterday";
    } else {
      dateText = archiveDateFormat.print(dateTime);
    }

    String timeText = archiveTimeFormat.print(dateTime);

    return dateText + " " + timeText;
  }

  public static File createNewImageFile() {
    final DateFormat imageNameFormat =
        new SimpleDateFormat("'OnHand_'yyyy-MM-dd-HH-mm-ss'.jpeg'", Locale.US);
    File picturesOutputFolder = new File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "OnHand");

    String fileName = imageNameFormat.format(new Date());
    return new File(picturesOutputFolder, fileName);
  }
}
