// @ts-check
// `@type` JSDoc annotations allow editor autocompletion and type checking
// (when paired with `@ts-check`).
// There are various equivalent ways to declare your Docusaurus config.
// See: https://docusaurus.io/docs/api/docusaurus-config

import {themes as prismThemes} from 'prism-react-renderer';

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'MaxKey单点登录认证系统',
  tagline: '业界领先的IAM身份管理和认证产品',
  favicon: 'img/favicon.ico',

  // Set the production url of your site here
  url: 'https://www.maxkey.top',
  // Set the /<baseUrl>/ pathname under which your site is served
  // For GitHub pages deployment, it is often '/<projectName>/'
  baseUrl: '/doc',
  staticDirectories: ['static'],

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'Dromara', // Usually your GitHub org/user name.
  projectName: 'MaxKey', // Usually your repo name.

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  // Even if you don't use internationalization, you can use this field to set
  // useful metadata like html lang. For example, if your site is Chinese, you
  // may want to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'zh',
    locales: ['en','zh'],
    localeConfigs: {
      en: {
        htmlLang: 'en',
      },
      // You can omit a locale (e.g. fr) if you don't need to override the defaults
      zh: {
        htmlLang: 'zh',
      },
    },
  },

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          sidebarPath: './sidebars.js',
		      lastVersion: 'current',
          versions: {
            current: {
              label: '4.1.x',
              path: '',
            },
          },
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
            'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
        },
        blog: {
          showReadingTime: true,
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
            'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
        },
        theme: {
          //customCss: './src/css/custom.css','./static/font-awesome-4.7.0/css/font-awesome.min.css',
		      customCss: [require.resolve('./src/css/custom.css'),require.resolve('./static/font-awesome-4.7.0/css/font-awesome.min.css')],
        },
      }),
    ],
  ],
  scripts: [
    {
      src: 'https://www.maxkey.top/doc/jquery.min.js'
    }],
  plugins: [
    [
        '@docusaurus/plugin-content-docs',
        {
            id: 'about',
            path: 'about',
            //editUrl: `https://github.com/${organizationName}/${projectName}/edit/${branch}/website/`,
            routeBasePath: 'about',
            sidebarPath: require.resolve('./sidebars.js'),
            
        },
    ],
  ],
  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      // Replace with your project's social card
      image: 'img/docusaurus-social-card.jpg',
      navbar: {
        //title: 'MaxKey',
        logo: {
          alt: 'MaxKey Logo',
          src: 'images/logo_maxkey.png',
        },
        items: [
          {
            
            to: 'http://www.maxkey.top/',
            position: 'right',
            label: '首页',
          },
          {
            type: 'docsVersionDropdown',
            position: 'right',
            //dropdownItemsBefore: [{to: '/docs', label: '当前版本'}],
            dropdownActiveClassDisabled: true,
                },
          {
            
            to: 'https://www.maxkey.top/zh/about/cevsenterprise.html',
            position: 'right',
            label: '企业版',
          },
          /*{
            to: 'https://www.maxkey.top/zh/about/cevscas.html',
            label: '同类对比',
            
            items: [
              {to: '/about/welcome', label: '概述'},
              {to: '/about/news', label: '新闻动态'},
              {to: '/about/team', label: '项目团队'},
              {to: '/about/licenses', label: '项目许可证'},
              {to: '/about/dependency', label: '项目依赖'},
              {to: '/about/cevsenterprise', label: '企业版'},
              {to: '/about/cevscas', label: '同类对比'},
              {to: '/about/roadmap', label: '开发路线图'},
            ],
            position: 'right'
          },*/
          {
            type: 'localeDropdown',
            position: 'right',
          },
        ],
      },
	  /*
      footer: {
        style: 'dark',
        links: [
          {
            title: 'Docs',
            items: [
              {
                label: 'Tutorial',
                to: '/docs/intro',
              },
            ],
          },
          {
            title: 'Community',
            items: [
              {
                label: 'Stack Overflow',
                href: 'https://stackoverflow.com/questions/tagged/docusaurus',
              },
              {
                label: 'Discord',
                href: 'https://discordapp.com/invite/docusaurus',
              },
              {
                label: 'Twitter',
                href: 'https://twitter.com/docusaurus',
              },
            ],
          },
          {
            title: 'More',
            items: [
              {
                label: 'Blog',
                to: '/blog',
              },
              {
                label: 'GitHub',
                href: 'https://github.com/facebook/docusaurus',
              },
            ],
          },
        ],
        copyright: `Copyright © ${new Date().getFullYear()} My Project, Inc. Built with Docusaurus.`,
      },*/
	  footer: {
        style: 'dark',
        copyright: `<a href="https://dromara.org/zh/projects/" target="_blank"><img style=" width: 70px;" src="https://www.maxkey.top/doc/images/partners/dromara.jpg?202210250857" title="Dromara"></img></a><img style="height: 70px;" src="https://www.maxkey.top/doc/images/dromara/zsxq.jpg" title="Dromara"></img><br/>Copyright © ${new Date().getFullYear()} <a href="https://www.maxkey.top/">maxkey.top</a> of Dromara . All rights reserved . Built with Docusaurus.`,
      },
      prism: {
        theme: prismThemes.github,
        darkTheme: prismThemes.dracula,
        additionalLanguages: ['powershell','java','php','python']
      },
    }),
};

export default config;
