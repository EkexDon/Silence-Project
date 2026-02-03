import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "SILENCE | Silent Observer",
  description: "An anti-social journal for the intentional soul.",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        <main className="min-h-screen">
          {children}
        </main>
      </body>
    </html>
  );
}
