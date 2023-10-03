# Truecaller Software Engineer Assignment

The app should provide a button to load the content of a website below and perform 3 tasks in
parallel. The app should display the result of each task as soon as it is done. You can choose how
you want to display the result.

## Installation

Download the zip file, extract it, and import it into Android Studio. After the successful gradle sync. Run the project. 
OR
Run the assignment-debug.apk file in your phone.


## Solution

The code follows MVVM architecture pattern.

Language - Kotlin

The fetch button uses Jsoup to download the content of the website in the Repository. The fetch button calls the ViewModel taking a parameter (url). The content is observed using LiveData. When the result is available, the LiveData posts values to the MainActivity in the background.

When the content is available, there are three tasks that run parallel to each other using CoroutineScope for executing asynchronous tasks within the ViewModel. Each task when available with the result posts value to MainActivity using LiveData.

Each task takes O(n) Time Complexity to perform.

1 - ```find15thCharacter```: It returns the value at index 14.

2 - ```findEvery15thCharacter```: We create a mutable list of characters and start a while loop from index 14 till the length and add the content at that index in the list and increment the index with 15 steps. The resultant list is then published using LiveData.

3 - ```countWords``` : It returns a Map with finding the occurrence of each word. The map is used here because we need the key to be unique and values can be duplicated. It starts with a while loop and runs till the content length and finds the occurrence of 'space', 'tab', and 'new line'. The index of the word end keeps increasing until this occurrence and then takes that word using substring and increments the count of that key if already there by 1. This process works before the last word. Then a condition is added to extract the last word. The resultant mao is pushed to MainActivity using LiveData.
