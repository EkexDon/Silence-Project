# Wiki Documentation

This directory contains the GitHub Wiki content for the SILENCE project.

## About GitHub Wikis

GitHub wikis are typically maintained in a separate git repository with a `.wiki` suffix (e.g., `Silence-Project.wiki`). This directory contains the wiki content that can be:

1. **Pushed to GitHub Wiki**: Clone the wiki repository and copy these files
2. **Used as Reference**: Read directly from the main repository
3. **Converted to GitHub Pages**: Use as documentation for GitHub Pages

## Wiki Pages

### Main Pages
- **[Home.md](Home.md)** - Welcome page and project overview
- **[Setup.md](Setup.md)** - Installation and setup instructions
- **[Architecture.md](Architecture.md)** - System architecture and design
- **[API.md](API.md)** - REST API documentation
- **[Coding-Standards.md](Coding-Standards.md)** - Development standards and best practices

### Architectural Decision Records (ADRs)
- **[ADR-Index.md](ADR-Index.md)** - Index of all ADRs
- **[ADR-0001-Technology-Stack.md](ADR-0001-Technology-Stack.md)** - Technology stack selection
- **[ADR-0002-AI-Character-Integration.md](ADR-0002-AI-Character-Integration.md)** - AI Observer implementation
- **[ADR-0003-Journey-Reflection-Security.md](ADR-0003-Journey-Reflection-Security.md)** - Journey reflection and security

## How to Push to GitHub Wiki

If you want to publish these pages to the GitHub Wiki:

### Option 1: Using GitHub's Web Interface
1. Go to the repository on GitHub
2. Click on the "Wiki" tab
3. Create new pages with the same names as these markdown files
4. Copy the content from each file

### Option 2: Using Git (Recommended)
```bash
# Clone the wiki repository
git clone https://github.com/EkexDon/Silence-Project.wiki.git

# Copy wiki files
cp wiki/*.md Silence-Project.wiki/

# Commit and push
cd Silence-Project.wiki
git add .
git commit -m "Update wiki documentation"
git push origin master
```

## File Naming Convention

GitHub Wiki pages use the following naming conventions:
- Spaces in page titles become hyphens in URLs
- `Home.md` becomes the wiki home page
- Other files can be referenced by their filename without `.md` extension

## Maintaining the Wiki

When making changes to the wiki:
1. Update the markdown files in this directory
2. Commit to the main repository
3. Push changes to the GitHub Wiki repository (if using Option 2 above)

## Related Documentation

The project also has documentation in the `/documentation` directory:
- `documentation/API.md` - Original API documentation
- `documentation/Architecture.md` - Original architecture overview
- `documentation/Setup.md` - Original setup guide
- `documentation/CodingStandards.md` - Original coding standards
- `documentation/adr/` - Original ADR files

The wiki pages are enhanced versions of these documents with better formatting, cross-linking, and additional context for GitHub Wiki readers.
