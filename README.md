# owncloud-bookmarks-android
A bookmarks Android app for ownCloud.

## features
- Display bookmarks
- Show favicons
- Store credentials

## open features
- Hash credentials (needs server side change)
- Edit bookmarks
- Delete bookmarks
- Create bookmarks
- Cache bookmarks on device

## Issues
- The credentials are sent via GET in plaintext.
  This means, even under https they are not properly protected.
  This has to be fixed on the server side.