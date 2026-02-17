# MCMapArt | Download the Runnable Jar [HERE](https://drive.google.com/drive/folders/1lFtJU2ibZLEljpUAE6YWGG5vqCWjruTa?usp=sharing)

### MCMapArt is a program for converting images into Minecraft maps.

The app is simply a runnable JAR file - run it to open the interactive window (requires Java 8 or above). With the window open, type the name of an image located in the same folder as the JAR (or the full path of an image located elsewhere) and click “Generate Map”. If the loading is successful, a small image of the newly generated map will appear on-screen. From here, there are two methods for putting these maps into your Minecraft world.

### Method 1: Export to Files
(recommended for Creative Mode)

With a map loaded into the program, clicking the “Export to Files” button will cause one or more files to be generated in the same directory as the JAR. Each file represents one map - an image larger than 128x128 pixels will result in multiple maps, which can be pieced together in-game to form the complete image.
To import these maps into a Minecraft world, simply go to the “data” folder in the world’s directory and place the files there. Make sure the file names are not changed - they should all look like `map_#.dat` where `#` is some number. If the world is currently open (on a client or server), shut it down before importing the files, then restart it once they’ve been imported.
Upon re-entering the world, make sure you have command permissions, and type the following command:

`/give @s filled_map[map_id=#]` (1.20.5+)

`/give @s filled_map{map:#}` (before 1.20.5)

where `#` is the same number as `map_#.dat`. (If you’re importing multiple files, run this command for each file you imported.) You should now be holding map item(s) containing the image you just imported. Images consisting of multiple maps are best viewed with multiple item frames!

### Method 2: Building a Map By Hand
(for the people who think Method 1 is cheating)

The program contains several tools to help a player build a map manually.

While a map is loaded, the “View Interactive Map” button will open a window to fully display every pixel on the map, which can be resized using the “Scale” setting. Mouse over any pixel to see its x-z coordinates (the top-left pixel is at the coordinates specified by the x and z selectors under "Top-Left Corner") as well as its material category (see the [Minecraft Wiki Color Table](https://minecraft.wiki/w/Map_item_format) for a list of which materials belong to which category). A material category with the suffix `_LIGHT`, `_DARK`, or `_SHADE4` means that the color is a non-neutral variant of that category (Note 3).

The “View Height Map” button opens a window displaying a single north-south strip of the map, where the z-axis is on the horizontal and the y-axis is on the vertical. The x-coordinate is at a constant value across the image, and can be changed with the number setting underneath the button. As before, mousing over a pixel will display its x-z coordinates and material. The height map represents a recommended configuration for blocks in that strip, to achieve the desired effect. These height variations are only relevant when Height Shades are on and Shade4 is off (Note 3).

Finally, pressing “View Materials” will display the full list of materials required to build this particular map, as specified by the Color Table.

### Notes
1) Maps are generated at precisely a 1:1 pixel-to-pixel scale with the image provided, regardless of the image’s size. However, Minecraft maps are always exactly 128x128 pixels. As a result, it’s recommended to use images with edge lengths that are multiples of 128, so that the resulting maps are completely filled. If the image doesn’t exactly match these dimensions, the maps exported by Method 1 (above) will have regions of unfilled pixels (but will still form a complete image), and the instructions for building via Method 2 will contain no information on the map’s remaining pixels.
2) When importing map files via Method 1, you may find that the world folder already contains one or more files with the same names as your new maps. These are existing maps that are already in your world. You can either delete the existing maps and insert the new ones, or change the names of the new ones to numbers that aren’t already present. You can also change the “Starting Map ID” setting before exporting.
3) To the right of the Generate Map button are two check-boxes: Use Height Shading (on by default) and Use Shade4 (off by default). These control the range of the color palette available to the program, and they’re applied when a new map is generated. Each material-based color has four variants: the neutral color, a lightened variant, a darkened variant, and an extra-dark variant known as Shade 4. As above, see [Map Item Format](https://minecraft.wiki/w/Map_item_format) for info on how these shades are achieved when building by hand - put simply, Height Shades (the lighter and darker variants) allow for more detailed images but are challenging to build, and Shade 4 is only obtainable via Method 1.