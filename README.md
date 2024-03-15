# DisruptAI

DisruptAI is revolutionizing advertising with our next-generation platform tailored for ad campaign managers, influencers, and dropshippers. Our specialty lies in crafting hyper-targeted advertisements using cutting-edge Generative AI technology.

#### URL - _________________

### Key Features:

- **Targeted Advertisement Generation:** Create advertisements tailored to your target demographic and purchase impressions seamlessly. Our AI algorithms analyze user behavior and preferences to ensure your ads reach the right audience.

- **Full AutoPilot:** Experience automated campaigns using sample product images, with dynamic changes to keep your campaigns fresh. Our platform continuously optimizes your ads based on performance data, ensuring maximum ROI.

- **Text Generation with Ads via OpenAI:** Utilize advanced AI to generate compelling ad copy. Our platform can generate engaging headlines, taglines, and body text that resonate with your audience.

- **Voice Over Integration via OpenAI:** Add professional voice-overs to your ads with ease. Our platform can generate natural-sounding voice-overs in multiple languages and accents, enhancing the impact of your ads.

- **Future Video Generation with Sora:** Soon, you'll be able to create video ads effortlessly. Our integration with Sora will allow you to generate high-quality video ads using AI, saving you time and resources.

### Plan of Action:

- [X] Create git repo
- [ ] Establish a connection to Midjourney programmatically, as they don't provide APIs. Explore alternative methods for integrating with Midjourney's platform to access their data securely.
- [ ] Make connection to OpenAI 
    - Leverage ChatGPT to craft prompts to feed into midjourney based on user input
- [ ] Create a spring API in Intellij
  - Create a REST API
  - Have it listened to a port
  - Receive a POST call
  - Configure a workflow
  - Design basic database
  - Retrieve user pictures for generating ad responses from DynamoDB
  - Call openAI and Midjourney
  - Return image response to user
- [ ] Develop a sample UI
    - Incorporate Midjourney UI elements
    - Enable users to submit sample pictures, stored in a DynamoDB
    - Consider hosting on AWS Amplify to utilize local endpoints and minimize costs
    - Test Connections
- [ ] Implement functionality to purchase impressions on Google Adx/Pubmatic. Explore the APIs provided by Google Adx/Pubmatic to integrate impression purchasing into your platform.
- [ ] Investigate if these platforms provide demographic data for enhanced targeting capabilities. If not, consider other data sources or methods for gathering demographic information.
- [ ] Allow ad campaign managers to submit requests with demographic targeting, budget, and sample product pictures. Create an intuitive interface for campaign managers to easily create and manage their campaigns.
- [ ] Explore options for running automated campaigns. Research automation tools and algorithms that can optimize ad campaigns based on performance data.

*Work in Progress*
