# KivaBot

KivaBot is tool for the MMORPG [Freewar](https://www.freewar.de), which allows the automatic
collection of resources.

The available documentation can be found in [our wiki](https://github.com/ZabuzaW/KivaBot/wiki).

## Setup

When launching, the login window shows and allows entering credentials, the world (Freewar server),
as well as the desired browser to use and configurations for which resources to collect and how to
get there.

Clicking _Settings_ allows configuring details.

| Login                                 | Settings                                 |
|---------------------------------------|------------------------------------------|
| ![Login](https://i.vgy.me/eRayVM.jpg) | ![Settings](https://i.vgy.me/T3Fw68.jpg) |

It is necessary to enter the executable (binary), and the Selenium driver (Driver) of the browser to
use. For Firefox, optionally, a User Profile can be given.

## UI

The bottom of the window shows the logs of the bot and the routine. Once started, the bot will log
in and automatically move to collect the selected resources one by one. Once it is done, it will log
out again.
