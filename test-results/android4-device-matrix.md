# Android 4 - Device Test Matrix

| Android Version | API Level | Device | Screen Size | Scenario 1 | Scenario 2 | Scenario 3 | Result |
|---|---:|---|---|---|---|---|---|
| Android 13 | 33 | Pixel 6 | 1080 x 2400 | PASS | PASS | PASS | 3/3 PASS |
| Android 14 | 34 | Not tested | Not tested | NOT RUN | NOT RUN | NOT RUN | NOT RUN |
| Android 15 | 35 | Not tested | Not tested | NOT RUN | NOT RUN | NOT RUN | NOT RUN |

## Notes

- All three required conversation scenarios were successfully executed on Android 13 / API 33.
- Tests use the Page Object Model structure with separate pages, steps, and tests.
- The existing project swiper helper is used for older-message navigation.
- Swipe coordinates are calculated from the messages RecyclerView dimensions to support different screen sizes.
- Older-message search uses a bounded number of swipe attempts.
- Android 14 / API 34 and Android 15 / API 35 were not executed and are therefore not reported as passed.