CWAC Layouts: Custom Containers, Concisely Coded
================================================

This project has a handful
of Android containers (`ViewGroup` implementations) designed to handle
specific scenarios: 

- `AspectLockedFrameLayout` that resizes itself, and its children, to
fit within a specific aspect ratio

- `MirroringFrameLayout` and an associated `Mirror` that duplicates and
scales the contents of the `MirroringFrameLayout` onto the `Mirror`

This Android library project is 
[available as a JAR](https://github.com/commonsguy/cwac-layouts/releases)
or as an artifact for use with Gradle. To use that, add the following
blocks to your `build.gradle` file:

```groovy
repositories {
    maven {
        url "https://s3.amazonaws.com/repo.commonsware.com"
    }
}

dependencies {
    compile 'com.commonsware.cwac:layouts:0.4.0'
}
```

Or, if you cannot use SSL, use `http://repo.commonsware.com` for the repository
URL.

**NOTE**: The JAR name, as of v0.4.1, has a `cwac-` prefix, to help distinguish it from other JARs.

Usage: AspectLockedFrameLayout
------------------------------
`AspectLockedFrameLayout` inherits from `FrameLayout`, and so you start by
setting it up much like you would any other `FrameLayout`. In fact, if you do
nothing else, it behaves the same as a regular `FrameLayout`.

However, if you call `setAspectRatio()` (supplying a `double` of the aspect
ratio you want) or `setAspectRatioSource()` (supplying a `View` whose aspect
ratio should be matched), then `AspectLockedFrameLayout` will reduce its height
or width to maintain the requested aspect ratio. This affects the children of
the `AspectLockedFrameLayout` as well, of course.

You can also use `lockAspectRatioImmediately` or `lockAspectRatioOnLayoutChange` to straightaway lock the `AspectLockedFrameLayout` aspect ratio to its own.

Since the `AspectLockedFrameLayout` effectively overrides and reduces its size,
you can use `android:layout_gravity` to position the shrunken
`AspectLockedFrameLayout` within its parent:

```xml
<FrameLayout
	android:layout_width="match_parent"
	android:layout_height="match_parent">

	<com.commonsware.cwac.layouts.AspectLockedFrameLayout
		android:id="@+id/source"
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:layout_gravity="center">

		<!-- children go here -->
	</com.commonsware.cwac.layouts.AspectLockedFrameLayout>
</FrameLayout>
```

Note that the resizing only takes place when the `AspectLockedFrameLayout`
is measured. If you change the aspect ratio, call `requestLayout()` on
the `AspectLockedFrameLayout` (or any parent container) to get it to resize.

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

Note that `MirroringFrameLayout` inherits from `AspectLockedFrameLayout` and sets its
aspect ratio to match that of the `Mirror`. This ensures that the `Mirror` will be
filled without some type of anamorphic stretching.

You are welcome to implement the `MirrorSink` interface on something and use that
as the destination for the mirroring, rather than use a `Mirror`.

Dependencies
------------
This project has no dependencies and should work on most versions of Android, though
it is only being tested on API Level 8+.

Version
-------
This is version v0.4.2 of this module, meaning it is coming along nicely.

Demo
----
In the `demo/` sub-project you will find
a `SimpleMirrorActivity` activity and a `WebMirrorActivity` that each
demonstrates the use of `MirroringFrameLayout` and `Mirror`.

License
-------
The code in this project is licensed under the Apache
Software License 2.0, per the terms of the included LICENSE
file.

Questions
---------
If you have questions regarding the use of this code, please post a question
on [StackOverflow](http://stackoverflow.com/questions/ask) tagged with
`commonsware-cwac` and `android` after [searching to see if there already is an answer](https://stackoverflow.com/search?q=[commonsware-cwac]+layouts). Be sure to indicate
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
- v0.4.2: updated for Android Studio 1.0 and new AAR publishing system
- v0.4.1: updated Gradle, fixed manifest for merging, added `cwac-` prefix to JAR
- v0.4.0: switched to `ViewTreeObserver` for more reliable updating; added `MirrorSink`
- v0.3.0: migrated to support Gradle and publish an AAR artifact
- v0.2.2: improved efficiency and resiliency to source/mirror issues
- v0.2.1: fixed `AspectLockedFrameLayout` to handle zero height/width better
- v0.2.0: added `AspectLockedFrameLayout` and used for `MirroringFrameLayout`
- v0.1.0: initial release

Who Made This?
--------------
<a href="http://commonsware.com">![CommonsWare](http://commonsware.com/images/logo.png)</a>

