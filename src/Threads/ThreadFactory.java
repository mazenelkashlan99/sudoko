package Threads;

public abstract class ThreadFactory{
    int threadNum;
    
    ThreadFactory(int threadNum){
       this.threadNum=threadNum;
    }

    // switch (threadNum) {
    //         case 0:
    //             int rowIndex=1;
    //             int columnIndex=1;
    //             int boxIndex=1;
    //             for (var row : rows){
    //                Elements rowElements = new Elements(row,Role.ROW,rowIndex);
    //                rowElements.validityOfRole();
    //                rowIndex++;
    //             }
    //             for (var col : columns){
    //                Elements columnElements = new Elements(col,Role.COLUMN,columnIndex);
    //                columnElements.validityOfRole();
    //                columnIndex++;
    //             }
    //             for (var box : boxes){
    //                 Elements boxElements = new Elements(box,Role.BOX,boxIndex);
    //                 boxElements.validityOfRole();
    //                 boxIndex++;
    //             }
    //             break;
    //         case 3:
    //             break;
    //         case 27:
    //             break;
    //         default:
    //             break;
    //     }

}