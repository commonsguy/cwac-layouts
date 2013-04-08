CWAC Layouts: Custom Containers, Concisely Coded
================================================

This Android library project (also
[available as a JAR](http://misc.commonsware.com/CWAC-Layouts.jar))
has a handful
of Android containers (`ViewGroup` implementations) designed to handle
specific scenarios: 

- `MirroringFrameLayout` and an associated `Mirror` that duplicates and
scales the contents of the `MirroringFrameLayout` onto the `Mirror`

Usage: Mirroing
---------------
The mirror classes are specifically designed for use with Android 4.2's
`Presentation` class, specifically for actual presentations. In a presentation,
the presenter needs to be able to see and control some widgets, but the
audience simply needs to see the widgets via a projector or other external
display. `MirroringFrameLayout`, used as a container around the control
widgets, will clone its output onto the `Mirror` `View`, which in turn
can be hosted by a `Presentation`.

To mirror a widget or widget hierarchy, wrap it in a `com.commonsware.cwac.layouts.MirroringFrameLayout`:

```xml
	<com.commonsware.cwac.layouts.MirroringFrameLayout
		android:id="@+id/source"
		android:layout_width="1000px"
		android:layout_height="0dp"
		android:layout_weight="1"
		android:background="#88FF0000">

		<WebView
		android:id="@+id/webkit"
		android:layout_width="match_parent"
		android:layout_height="match_parent"/>

	</com.commonsware.cwac.layouts.MirroringFrameLayout>
```

Also add a `com.commonsware.cwac.layouts.Mirror` widget where you want the duplicate
contents to be rendered.

Then, in your Java code, attach the `Mirror` to the `MirroringFrameLayout`:

```java
    MirroringFrameLayout source=
        (MirroringFrameLayout)findViewById(R.id.source);
    Mirror target=(Mirror)findViewById(R.id.target);

    source.setMirror(target);
```

And that's it.

This should work for all widgets except `SurfaceView` and perhaps `TextureView`, plus
things derived from them (e.g., `VideoView`, Maps V2 `MapView`).

Dependencies
------------
This project has no dependencies and should work on most versions of Android, though
it is only being tested on API Level 8+.

Version
-------
This is version v0.1.0 of this module, meaning it is brand-spankin' new.

Demo
----
In the `demo/` sub-project you will find
a `SimpleMirrorActivity` activity that demonstrates the use of `MirroringFrameLayout`
and `Mirror`.

License
-------
The code in this project is licensed under the Apache
Software License 2.0, per the terms of the included LICENSE
file.

Questions
---------
If you have questions regarding the use of this code, please post a question
on [StackOverflow](http://stackoverflow.com/questions/ask) tagged with `commonsware` and `android`. Be sure to indicate
what CWAC module you are having issues with, and be sure to include source code 
and stack traces if you are encountering crashes.

If you have encountered what is clearly a bug, or if you have a feature request,
please post an [issue](https://github.com/commonsguy/cwac-layouts/issues).
Be certain to include complete steps for reproducing the issue.

Do not ask for help via Twitter.

Also, if you plan on hacking
on the code with an eye for contributing something back,
please open an issue that we can use for discussing
implementation details. Just lobbing a pull request over
the fence may work, but it may not.

Release Notes
-------------
- v0.1.0: initial release

Who Made This?
--------------
<a href="http://commonsware.com">![CommonsWare](http://commonsware.com/images/logo.png)</a>

