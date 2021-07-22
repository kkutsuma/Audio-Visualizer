# Audio Visualizer

## By: Keith Kutsuma & Kelsey Valencia

_Created for CU Boulder CSCI 4448 SU21 Final Project_

### Demonstration

[Demonstration Video](https://www.dropbox.com/s/la2cil1wn6u1x3r/Demonstration.mp4?dl=0)

### System Statement

Our Audio Visualizer is now capable of reading .mp4, .mkv, .mp3, .wav files, and [more](https://docs.oracle.com/javafx/2/api/javafx/scene/media/package-summary.html). Any valid file given will be played back with the addition of an representation of the audio being played back.

![Audio Visualizer Example](https://github.com/kkutsuma/CSCI4448_Lab6/blob/README_Images/updatedAudioVizExample.jpg)

The Audio Visualizer is also capable of multiple color settings, different amounts of bands, and adding valid media files. This is all on top of the normal capabilities of a media player such as pause, resume, skip, and previous media selections.

Some of the changes made from project 5 that did not make it into the final project are saving songs to one folder, deleting songs, and choosing which songs to play. This is because we realized it would take a lot more formatting and UI design to properly list and include the delete functions that we would want so it wasn't included for this sprint.
The largest reason to this is because we changed our requirements towards copying songs and we will get to that in the next section.

From project 6 we made major changes. Starting with the file system we added the capability to play more than one hard coded song, have the songs run continuously, skip/return to another song, and adding new songs. Also we added two types of visualizers and the option to change the amount of spectrum bars you can observe. We also worked on the UI and redesigning the program to look better. This took more time than expected since we changed our file structure and how we set-up the classes due to this.

### Comparison Statement

#### Key Points & Changes

Here are the Project 5 and the Final Class Diagrams.

![Final Class Diagram](https://github.com/kkutsuma/CSCI4448_Lab6/blob/README_Images/Final_Class_Diagram.png)
**Final Class Diagram**

![Previous Class UML](https://github.com/kkutsuma/CSCI4448_Lab6/blob/README_Images/Class_Diagram_Project5.jpg)
**Project 5 Class Diagram**

We can see that the final class diagram looks like a trimmed down version of the project 5 class diagram. And that is because it is. Many of the classes such as the SceneChanger, DataBaseHandler, SettingsController, Property, and the extra .fxml files weren't needed. In fact most of the .fxml files were excluded from our Project 6 because it didn't give us enough flexibility to handle the visualizations that we wanted.

As such Kelsey took Project 6 and slimmed it down to the version now. Now there is one MediaPlayer.fxml that handles the visualizations of the main page. This includes the placement of the bars, visualizer, buttons, etc. In addition to handling the styling of this page.

Then there is the MyMediaPlayer which is the controller for MediaPlayer.fxml. This handles all of the information needed to play the MediaPlayer. It sets the listeners and delegates the work to the SceneController how to update the visualizer. In addition the MyMediaPlayer handles any of the menu buttons which will change the visualizers. Finally this class will delegate work to the FileManager to get a new song from the user and add it to the current list of songs.

Otherwise the Implementations of the SceneController create the visualizations that we want to have. In our cases they're rectangles to represent the bars. start(Integer, AnchorPane) will set the bars position/values. Then update(double, double, float[], float[]) will change the bar heights depending on the values that the media player's observer is giving it.

Going back to the FileManager we felt that it wasn't needed to have a sql database to handle the information for this project. It was overboard, and we didn't want to make instillations for the team and others to be more difficult. So we went with handling the song information in a csv, and using java's io writer/reader to work with the file. It didn't require any more libraries and was a simple implementation.

#### Design Patterns

Since Project 6 we took out the command pattern since it wasn't needed, *(more isn't always better)* and added a strategy pattern. This is so that the MyMediaPlayer can simply run update on the current scene and it will change the values of the bars, *(or what we are using as to represent a band)*.

We thought that this was more important because while it makes the scene itself more difficult to change, it makes the visualizer much easier to customize and design. This was more of the headstone and main goal we wanted which is why we took this approach.

Otherwise we are also using the built in observer pattern to update the visualizer to any changes in the music. Also both the volume bar and timing bar use observers to update their values or the songs value.

### Third-Party Code vs Original Code

Firstly the front end API of JavaFX and any java libraries were not coded by us. Although we did use them heavily in this project.

In addition the .fxml file, while it was made by us we did use SceneBuilder which is a UI based tool to generate the code in order to create the scene visuals we want.

The file system used Java's Read/Write libraries to read, write, and find the .csv file. We didn't use an external library for the csv and used really simple read/write statements. Resources we used for this can be found [here.](https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/)

The controls of the project such as the volume slider, timer bar, and buttons use basic functions of the JavaFX API and we use multiple techniques. For both slider's we use different ways to control them because they each have slightly different needs. The volume bar used a way that [Oracle handled a slider](https://docs.oracle.com/javase/8/javafx/media-tutorial/playercontrol.htm), but didn't handle the timer bar how we wanted so Kelsey found another way to do that. Most of the standard practices can be found on [Oracle's website and this](https://docs.oracle.com/javase/8/javafx/get-started-tutorial/index.html) was a resource that helped.

For the visual graphic design portion of our project, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Rectangle.html was a great resource used to help understand the properties and methods that make up the Rectangle shape Javafx class. Additionally, https://www.tutorialspoint.com/javafx/javafx_colors.htm was also a useful resource to look at when trying to implement different color patterns such as, the linear gradient pattern used in the scene, "Rainbow".

### Final Statements on OOAD

1. Keith found it difficult during the designing phase especially around JavaFX since we didn't know much about it. It was difficult to determine how the code needed to be handled and what was best practice. Since working with more packages/libraries, they can be designed in a way that isn't Object Oriented Friendly. Or they have an aspects that have difficult give or takes. In our case learning how to deal with .fxml files was difficult and took many prototypes until a decent design was even made.

2. Another point Keith saw was that our group worked in more of a scrum style. In the end when we started to understand more about JavaFX and working together our results were quicker and seemed to work better. Given more scrums the project was on track to have some impresive results. The issue being that we don't have continuous amounts of time to work and should have treated it more like a waterfall style since we have a hard deadline for the class.

3. Kelsey encountered numerous problems when trying to implement the file chooser feature included in our final draft of our project. The main problem being the excess use of multiple .fxml files and loading all of them using the FXML loader in our previous draft of our project. Kelsey found a way to work around this issue by restructering the overall program layout and removing all extra .fxml files except for our main one.

### Project Set Up

To work on this project we are using JavaFX 16 & Java 16. You can find the latest JavaFX download [here](https://gluonhq.com/products/javafx/). To set up JavaFX you can find instructions [here](https://openjfx.io/openjfx-docs/). You can then run the main function if you want to run the program there.

There is also a built .jar file **AudioViz.jar** which can be executed with the vm options

```
--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml,javafx.media
```

Where the ${PATH_TO_FX} is your own path to the JavaFX lib folder.

# MIDPOINT UPDATE [DEPRECATED]

### Status Summary

![Audio Visualizer Example](https://github.com/kkutsuma/CSCI4448_Lab6/blob/README_Images/audioVizExample.jpg)

#### Work Done

This past sprint has been spent prototyping and learning about JavaFX's API and creating the UI and visualizer for the application. Currently we have the visualizer created, pause and resume functions, the main scene UI, volume bar, and music time bar.

First, both of us worked on prototyping and learning about JavaFX before working on the main project. Then we moved to create changes and begin merging what we prototyped into one package. Since most of the code was already available from us prototyping Keith worked on putting the code for this repository together. In addition Keith already had the UML from project 5 so he handled that as well. Some of our largest concerns was making sure the project's files were set up well if someone else were to look at our work which is why we did lots of prototypes before finally creating the repository.

Here is some of the information we gained from prototyping first.

1. JavaFX .fxml files work 1-1 (.fxml - java controller), and will load/instantiate when called upon.
2. JavaFX MediaPlayer has an observer pattern for active/current media data that is being played. This means that if there's no sound there will be no information being feed to the listener.
  * What this meant for us is that if the music is paused and you reload the visualizer page there will be no information to update the scene.
  * We escalated this and it may be possible to cache this information upon page swapping but will have to be done at another time.
3. This is more of a development tip, but creating UI solely by fxml is not always the best way to do it. We had to change from using fxml to normal code to generate the visualizer's bars because we needed a way to reference each bar, and manually creating it in fxml and instantiating an array manually isn't a good idea.
4. At the start of the sprint we prototyped the MediaPlayer and how to load, play, and pause mp3 information.
5. Then we prototyped the scene changer. Doing some research [this](https://gist.github.com/jewelsea/6460130) has been considered as the best practice to handle fxml files. It is an upgrade to one of Oracle's examples of best practices. In addition we added a loader class so that we only have to change that class if we want to add more fxml files.

#### Changes & Issues

Because we have continued to prototype we had to make some changes to the design already. We still have the scene changer to control changing the main scenes. But within the visualizer scene we've broken it up into smaller fxml files. There are now three files, one controls the main part of the scene, one for the volume/timer bars, and one for the scene's buttons, (pause, resume, next page).

Because each of the pages have a separate controller, but all need to use the same MediaPlayer, we created a singleton class that will hold our MediaPlayer. It will hold one instance and each controller can get the media when the page loads.

This allows us to **not** store all of the actions of a scene into **one** controller. Instead we have multiple controllers and can change each module if we like. Because of this design, it should allow us to load different types of visualizer's to have different types of displays if we want.

In addition we ran into the issue that the current graph.fxml file isn't good coding practice and we are in need to change how the file's structured. Instead we need the graphController to generate a UI for the graph and hold onto a reference to each bar of the graph. This is so that the graphController can manipulate the bars to show the audio spectrum's visuals. We think the best way right now is to create a strategy pattern and have that strategy have different types of UI generation. This way we can easily change or add to the method's behavior.  

#### Patterns

1. First we have our singleton of the player. This was described before, but it allows us to load the media and use it in multiple controller classes since we separated the functions within each module.
2. Second we have a command pattern used in the SceneController and SceneLoader. This is used to change main scene by loading different .fxml files. The SceneLoader has a static function to instantiate a SceneController. This allows us to easily change what .fxml files we want to change. Then the SceneController is a singleton so that we can access the functions anywhere downwards in the scenes. Such that we can change by using a button press in another .fxml's controller.
3. We are also using observers that are build in to JavaFX. The main one is the AudioSpectrumListener that takes the current .mp3 output and changes the height of each bar in the graph based on the music's magnitudes per band.
  * Technically there is a lot happening with button presses and setting onActions but they are encapsulated to us so I won't add it here.

### Next Sprint

Next spring we need to finish up the program. Currently this includes creating the file system, file system scene, adding/removing songs, and changing songs. We also have one change that we want to make to the previous work. This is adding a strategy pattern to the visualizer. Specifically the shapes of the UI so that we can change how many shapes, the shapes color, and the shape type if we want to. We are projecting that the File Management will the most difficult part of this sprint. But because we have mostly medium's, *(look below)* we plan to have this functionality completed by 7/21.

*Next Sprint Checklist*

* [M] Create File System UI
* [M] Create Controllers for .fxml files
* [L] Add file management class
  * [S] May require creating a song class to hold song information
* [M] Add change song functionality
* [S] Add ability to change visualizer settings
* [S] Add CSS & Update UI

### Class Diagram

![Current Class UML](https://github.com/kkutsuma/CSCI4448_Lab6/blob/README_Images/ClassDiagram.jpg)
