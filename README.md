# Pokémon Ranger Guardian Signs: Container extractor
A Java program that extracts the entries from simple container files in Pokémon Ranger Guardian Signs. It is a command line program;
use ```java -jar RangerParser.jar -filename``` to extract the contents of a file.

# Container format
The file has one header which is followed by the entries. The header is 0x10 bytes:
- 0x0 - int32: The signature / type
- 0x4 - int32: Entry size
- 0x8 - int32: Number of entries
- 0xC - int32: Useless; always zero
